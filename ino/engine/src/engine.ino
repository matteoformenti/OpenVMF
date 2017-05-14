#include <Servo.h>
#include <digitalWriteFast.h>

#define SERIAL_BAUD_RATE 115200
#define ENGINE_PIN 9
#define RELAY_0_PIN 2
#define RELAY_1_PIN 3
#define SERVO_PIN 9
#define MAX_INPUT_LENGTH 7

Servo steer;

void setup() {
  Serial.begin(SERIAL_BAUD_RATE);
  Serial.println("engine");
  steer.attach(SERVO_PIN);
}

void loop() {
  if (Serial.available() > 0)
  {
      char in[MAX_INPUT_LENGTH];
      Serial.readBytes(in, MAX_INPUT_LENGTH);
      analogWrite(ENGINE_PIN, (((int)in[0])-48)*100+(((int)in[1])-48)*10+(((int)in[2])-48));
      switch (in[6])
      {
        case '0':
        //forward
          digitalWriteFast(RELAY_0_PIN, HIGH);
          digitalWriteFast(RELAY_1_PIN, HIGH);
          break;
        case '1':
        //backward
          digitalWriteFast(RELAY_0_PIN, HIGH);
          digitalWriteFast(RELAY_1_PIN, HIGH);
          break;
        case '2':
        //brake
          digitalWriteFast(RELAY_0_PIN, HIGH);
          digitalWriteFast(RELAY_1_PIN, HIGH);
          break;
      }
      steer.write((((int)in[3])-48)*100+(((int)in[4])-48)*10+(((int)in[5])-48));
  }
}
