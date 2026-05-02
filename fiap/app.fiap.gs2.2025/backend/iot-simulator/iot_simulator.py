#!/usr/bin/env python3
"""
IoT Health Data Simulator
Simulates health monitoring devices (fitness trackers, smartwatches) 
that send health metrics to the backend API.
"""

import os
import time
import random
import requests
import logging
from datetime import datetime, timedelta
from typing import Dict, Optional

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Configuration from environment variables
API_BASE_URL = os.getenv('API_BASE_URL', 'http://api:8080')
API_ENDPOINT = f'{API_BASE_URL}/api/HealthMetric/iot'
USER_ID = int(os.getenv('USER_ID', '1'))
DEVICE_ID = os.getenv('DEVICE_ID', f'iot-device-{random.randint(1000, 9999)}')
DEVICE_TYPE = os.getenv('DEVICE_TYPE', 'fitness_tracker')
INTERVAL_SECONDS = int(os.getenv('INTERVAL_SECONDS', '300'))  # 5 minutes default
SIMULATE_STRESS = os.getenv('SIMULATE_STRESS', 'false').lower() == 'true'

class HealthDataSimulator:
    """Simulates health metrics from IoT devices"""
    
    def __init__(self, user_id: int, device_id: str, device_type: str):
        self.user_id = user_id
        self.device_id = device_id
        self.device_type = device_type
        self.base_heart_rate = 70
        self.base_steps = 0
        self.stress_level = 0  # 0-100 scale
        
    def generate_heart_rate(self) -> int:
        """Generate realistic heart rate (BPM)"""
        # Normal resting: 60-100 BPM
        # During activity: 100-150 BPM
        # High stress: 100-120+ BPM
        
        if SIMULATE_STRESS and self.stress_level > 50:
            # Elevated heart rate due to stress
            return random.randint(95, 120)
        elif random.random() < 0.3:  # 30% chance of being active
            return random.randint(100, 140)
        else:
            return random.randint(60, 90)
    
    def generate_steps(self) -> int:
        """Generate daily step count"""
        # Increment steps throughout the day
        hour = datetime.now().hour
        if hour < 6:
            # Early morning - low activity
            increment = random.randint(0, 50)
        elif hour < 12:
            # Morning - moderate activity
            increment = random.randint(100, 500)
        elif hour < 18:
            # Afternoon - higher activity
            increment = random.randint(200, 800)
        elif hour < 22:
            # Evening - moderate activity
            increment = random.randint(100, 400)
        else:
            # Night - low activity
            increment = random.randint(0, 100)
        
        self.base_steps += increment
        return self.base_steps
    
    def generate_sleep_hours(self) -> Optional[float]:
        """Generate sleep hours (only during night/morning)"""
        hour = datetime.now().hour
        if hour >= 22 or hour < 8:
            # Night time - simulate sleep tracking
            # Normal sleep: 7-9 hours, stress can reduce to 4-6 hours
            if SIMULATE_STRESS and self.stress_level > 50:
                return round(random.uniform(4.5, 6.5), 1)
            else:
                return round(random.uniform(7.0, 9.0), 1)
        return None
    
    def generate_heart_rate_variability(self) -> Optional[int]:
        """Generate HRV (Heart Rate Variability) in milliseconds"""
        # Lower HRV indicates higher stress
        # Normal: 40-60ms, Stressed: 20-40ms, Very stressed: <20ms
        if SIMULATE_STRESS and self.stress_level > 70:
            return random.randint(15, 25)
        elif SIMULATE_STRESS and self.stress_level > 50:
            return random.randint(25, 35)
        else:
            return random.randint(40, 60)
    
    def generate_body_temperature(self) -> Optional[float]:
        """Generate body temperature in Celsius"""
        # Normal: 36.1-37.2°C
        # Slight variations throughout the day
        base_temp = 36.6
        variation = random.uniform(-0.3, 0.5)
        return round(base_temp + variation, 1)
    
    def update_stress_level(self):
        """Simulate changing stress levels throughout the day"""
        hour = datetime.now().hour
        # Higher stress in afternoon (2-6 PM)
        if 14 <= hour <= 18:
            self.stress_level = min(100, self.stress_level + random.randint(1, 5))
        else:
            self.stress_level = max(0, self.stress_level - random.randint(1, 3))
    
    def generate_health_data(self) -> Dict:
        """Generate a complete health data payload"""
        self.update_stress_level()
        
        data = {
            'userId': self.user_id,
            'timestamp': datetime.utcnow().isoformat() + 'Z',
            'deviceId': self.device_id,
            'deviceType': self.device_type,
            'heartRate': self.generate_heart_rate(),
            'steps': self.generate_steps(),
            'heartRateVariability': self.generate_heart_rate_variability(),
            'bodyTemperature': self.generate_body_temperature()
        }
        
        # Add sleep hours only during appropriate times
        sleep_hours = self.generate_sleep_hours()
        if sleep_hours:
            data['sleepHours'] = sleep_hours
        
        return data
    
    def send_health_data(self, data: Dict) -> bool:
        """Send health data to the API"""
        try:
            response = requests.post(
                API_ENDPOINT,
                json=data,
                headers={'Content-Type': 'application/json'},
                timeout=10
            )
            
            if response.status_code == 200:
                logger.info(f"✓ Health data sent successfully: HR={data.get('heartRate')}, Steps={data.get('steps')}, HRV={data.get('heartRateVariability')}")
                return True
            else:
                logger.warning(f"✗ API returned status {response.status_code}: {response.text}")
                return False
        except requests.exceptions.ConnectionError:
            logger.error(f"✗ Connection error: Could not reach API at {API_ENDPOINT}")
            return False
        except requests.exceptions.Timeout:
            logger.error(f"✗ Timeout: API did not respond in time")
            return False
        except Exception as e:
            logger.error(f"✗ Error sending data: {str(e)}")
            return False
    
    def run(self, interval: int):
        """Run the simulator continuously"""
        logger.info(f"Starting IoT Health Data Simulator")
        logger.info(f"  Device ID: {self.device_id}")
        logger.info(f"  Device Type: {self.device_type}")
        logger.info(f"  User ID: {self.user_id}")
        logger.info(f"  API Endpoint: {API_ENDPOINT}")
        logger.info(f"  Interval: {interval} seconds")
        logger.info(f"  Stress Simulation: {SIMULATE_STRESS}")
        logger.info("")
        
        consecutive_failures = 0
        max_failures = 5
        
        while True:
            try:
                health_data = self.generate_health_data()
                success = self.send_health_data(health_data)
                
                if success:
                    consecutive_failures = 0
                else:
                    consecutive_failures += 1
                    if consecutive_failures >= max_failures:
                        logger.warning(f"Too many consecutive failures ({max_failures}). Waiting longer before retry...")
                        time.sleep(interval * 2)
                        consecutive_failures = 0
                
                time.sleep(interval)
            except KeyboardInterrupt:
                logger.info("Simulator stopped by user")
                break
            except Exception as e:
                logger.error(f"Unexpected error: {str(e)}")
                time.sleep(interval)

def main():
    """Main entry point"""
    simulator = HealthDataSimulator(USER_ID, DEVICE_ID, DEVICE_TYPE)
    simulator.run(INTERVAL_SECONDS)

if __name__ == '__main__':
    main()

