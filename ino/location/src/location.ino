#include <Servo.h>
#include <digitalWriteFast.h>
#include <PS2Mouse.h>

#define SERIAL_BAUD_RATE 115200
#define PS2_DATA 6
#define PS2_CLOCK 5

PS2Mouse mouse(PS2_DATA, PS2_CLOCK);
int x = 0;
int y = 0;
int h = 0;
uint8_t stat;
String out;

void setup() {
  Serial.begin(SERIAL_BAUD_RATE);
  Serial.println("location");
}

void loop() {
  if (Serial.available() > 0)
  {
      Serial.read();
      //mouse.getPosition(stat,x,y);
      out = x;
      out += ":";
      out += y;
      out += ":";
      out += h;
      Serial.println(out);
  }
}
