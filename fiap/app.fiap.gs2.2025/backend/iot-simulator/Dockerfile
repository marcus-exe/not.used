FROM python:3.11-slim

WORKDIR /app

# Install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the simulator script
COPY iot_simulator.py .

# Run the simulator
CMD ["python", "iot_simulator.py"]

