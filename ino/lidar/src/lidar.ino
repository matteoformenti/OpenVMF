#include <NewPing.h>
#include <digitalWriteFast.h>

#define SONAR_NUM     4
#define MAX_DISTANCE 200
#define STEP_PIN 10
#define DIR_PIN 11
#define STEPS_PER_REVOLUTION 400
#define DEGREES_PER_POSITION 15
#define STEPS_PER_POSITION (STEPS_PER_REVOLUTION*DEGREES_PER_POSITION)/360
#define POSITION_COUNT 7
#define STEP_DELAY_MICROSECONDS 500

//0,15,30,45,60,75,90
bool stepForward = true;
uint8_t currentPosition = 0;
String out = "";

//trigger, echo, distance
NewPing sonar[SONAR_NUM] = {
  NewPing(2, 3, MAX_DISTANCE),
  NewPing(4, 5, MAX_DISTANCE),
  NewPing(6, 7, MAX_DISTANCE),
  NewPing(8, 9, MAX_DISTANCE)
};

void setup() {
  Serial.begin(115200);
  Serial.println("lidar");
  pinModeFast(13, OUTPUT);
  digitalWrite(DIR_PIN, HIGH);
}

void loop() {
  if (Serial.available() > 0) {
    Serial.read();
    out = String(currentPosition);
    out+=":";
    for (uint8_t i = 0; i < SONAR_NUM; i++) {
      out += sonar[i].ping_cm();
      if (i != SONAR_NUM-1)
        out+=":";
      delay(10);
    }
    nextPosition();
    Serial.println(out);
  }
}

void nextPosition() {
  currentPosition+= ((stepForward)?1:-1);
  out += "";
  if (currentPosition == POSITION_COUNT-1 && stepForward)
    stepForward = false;
  if (currentPosition == 0 && !stepForward)
    stepForward = true;
  for (byte i = 0; i < STEPS_PER_POSITION; i++) {
    digitalWriteFast(STEP_PIN, !digitalReadFast(STEP_PIN));
    delay(10);
  }
  digitalWriteFast(DIR_PIN, stepForward);
}
