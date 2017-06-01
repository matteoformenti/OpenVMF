#include <Servo.h>
#include <digitalWriteFast.h>

#define SERIAL_BAUD_RATE 115200
#define ENGINE_PIN 6
#define RELAY_0_PIN 2
#define RELAY_1_PIN 3
#define SERVO_PIN 9
#define MAX_INPUT_LENGTH 7

Servo steer;

void setup() {
  pinModeFast(RELAY_0_PIN, OUTPUT);
  pinModeFast(RELAY_1_PIN, OUTPUT);
  digitalWriteFast(RELAY_0_PIN, LOW);
  digitalWriteFast(RELAY_1_PIN, HIGH);
  Serial.begin(SERIAL_BAUD_RATE);
  Serial.println("engine");
  steer.attach(SERVO_PIN);
  steer.write(150);
}

void loop() {
  if (Serial.available() > 0)
  {
      char in[MAX_INPUT_LENGTH];
      Serial.readBytes(in, MAX_INPUT_LENGTH);
      int a = (((int)in[0])-48)*100+(((int)in[1])-48)*10+(((int)in[2])-48);
      analogWrite(ENGINE_PIN, a);
      switch (in[6])
      {
        case '0':
        //forward
          digitalWriteFast(RELAY_0_PIN, LOW);
          digitalWriteFast(RELAY_1_PIN, LOW);
          break;
        case '1':
        //backward
          digitalWriteFast(RELAY_0_PIN, HIGH);
          digitalWriteFast(RELAY_1_PIN, HIGH);
          break;
        case '2':
        //brake
          digitalWriteFast(RELAY_0_PIN, LOW);
          digitalWriteFast(RELAY_1_PIN, HIGH);
          break;
      }
      int s = (((int)in[3])-48)*100+(((int)in[4])-48)*10+(((int)in[5])-48);
      Serial.println(a);
      steer.write(s);
  }
}
