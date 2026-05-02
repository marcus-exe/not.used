

#include <ESP32Servo.h>
#include <PubSubClient.h>
#include <WiFi.h>

#define THUMB_MAX 160
#define THUMB_MIN 50

Servo myservo1;
Servo myservo2;
Servo myservo3;
Servo myservo4;
Servo myservo5;// create servo object to control a servo

String msg;

const char* ssid = "Ocean_Adm";
const char* password = "@artico2020!";

const char* topic_sub1 = "arm-ocean-1";
const char* topic_sub2 = "arm-ocean-2";
const char* topic_sub3 = "arm-ocean-3";
const char* topic_sub4 = "arm-ocean-4";
const char* topic_sub5 = "arm-ocean-5";

const char* topic_all_hand = "arm-ocean";

//const char* topic_pub = "laps-ocean-iot-1";
const char* mqtt_id = "esp-445";

const char* BROKER_MQTT = "broker.hivemq.com";// URL of the MQTT broker to be used
int BROKER_PORT = 1883; // MQTT Broker Port

WiFiClient espClient;
PubSubClient MQTT(espClient);

void initMQTT(void);
void mqtt_callback(char* topic, byte* payload, unsigned int length);
void reconnectMQTT(void);
void VerificaConexaoMQTT(void);

/*

ATENÇÃO

- SEMPRE OBEDEÇA O MIN/MAX DO DEDÃO (THUMB)
- SEMPRE REMOVA O ESP-32 PRA DAR FLASH

*/

int thumb_filter(int a){
  return min(max(a,THUMB_MIN),THUMB_MAX);
}


void initMQTT(void)
{
    MQTT.setServer(BROKER_MQTT, BROKER_PORT);   // tells which broker and port to connect
    MQTT.setCallback(mqtt_callback);            // assign callback function (function called when any information from one of the subscript topics arrives)
}

void mqtt_callback(char* topic, byte* payload, unsigned int length)
{
    String msg;
    char a = '0';
    /* get the received payload string */
    for(int i = 0; i < length; i++)
    {
       char c = (char)payload[i];
       msg += c;
    }
    Serial.println("The following string arrived via MQTT:");
    Serial.println(topic);
    
    // THUMB
    if(String(topic) == topic_sub1){
       myservo1.write(thumb_filter(msg.toInt()));
       Serial.println(msg);
       //delay(15);
    }
    else if(String(topic) == topic_sub2){
       myservo2.write(msg.toInt());
       Serial.println(msg);
    }
    else if(String(topic) == topic_sub3){
       myservo3.write(msg.toInt());
       Serial.println(msg);
    }
    else if(String(topic) == topic_sub4){
       myservo4.write(msg.toInt());
       Serial.println(msg);
    }
    else if(String(topic) == topic_sub5){
       myservo5.write(msg.toInt());
       Serial.println(msg);
    }
    else if(String(topic) == topic_all_hand){
      Serial.println(msg);
      myservo2.write(!(bool)(int)(msg[1]-a)*180);
      myservo3.write(!(bool)(int)(msg[2]-a)*180);
      myservo4.write(!(bool)(int)(msg[3]-a)*180);
      myservo5.write(!(bool)(int)(msg[4]-a)*180);
//      delay(1000);
      myservo1.write(thumb_filter((msg[0]-a) * 180));
       Serial.println(thumb_filter((msg[0]-a) * 180));
    }
}

void reconnectMQTT(void)
{
    while (!MQTT.connected())
    {
        Serial.print("* Trying to connect to the MQTT Broker:");
        Serial.println(BROKER_MQTT);
        if (MQTT.connect(mqtt_id))
        {
            Serial.println("Successfully connected to the MQTT broker!");
            MQTT.subscribe(topic_sub1);
            MQTT.subscribe(topic_sub2);
            MQTT.subscribe(topic_sub3);
            MQTT.subscribe(topic_sub4);
            MQTT.subscribe(topic_sub5);
            MQTT.subscribe(topic_all_hand);
        }
        else
        {
            Serial.println("Failed to reconnect to the broker.");
            Serial.println("There will be another attempt to connect in 2s");
            delay(2000);
        }
    }
}

void VerificaConexaoMQTT(void)
{
    if (!MQTT.connected())
        reconnectMQTT(); // if there is no connection with the Broker, the connection is re-established
     
}
 

void setup() {

  Serial.begin(115200);
   WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
    }
  Serial.println("WiFi Connected .... IP Address:");
  Serial.println(WiFi.localIP());

  myservo1.setPeriodHertz(50);    
  myservo1.attach(19, 500, 2400); 
  myservo2.setPeriodHertz(50);    
  myservo2.attach(18, 500, 2400); 
  myservo3.setPeriodHertz(50);    
  myservo3.attach(5, 500, 2400); 
  myservo4.setPeriodHertz(50);    
  myservo4.attach(17, 500, 2400);
  myservo5.setPeriodHertz(50);    
  myservo5.attach(16, 500, 2400);
  initMQTT();
}

void loop() {


      VerificaConexaoMQTT();

    MQTT.loop();



}
