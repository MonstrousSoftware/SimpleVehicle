# SimpleVehicle

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff).

![screenshot](https://github.com/MonstrousSoftware/SimpleVehicle/assets/49096535/106bb8d5-64e7-4e4a-9e01-8b3900dfea98)

Demo of simple car driving physics without the use of a physics library.

The demo allows to accelerate (W) and brake (S), to steer (A, D) and to change gears (UP, DOWN).

Gears are Reverse, Neutral, 1 to 5. You can drive in reverse and put the car in neutral.

Limitations are due to not having an underlying physics model:
- This demo assumes a completely flat terrain, there is no collision detection.
- Note that the car does not lean in a curve and does not lean forward when braking or lean backward when accelerating.
- There is a fake understeer effect to avoid turning very fast at high speed (by clamping the car rotation speed).
- There is no oversteer effect, the tires have perfect grip. You cannot do wheel spin.

To develop this into a racing game, you would need to combine it with a physics library such as Bullet.



## Platforms

-  Primary desktop platform using LWJGL3.

## Further comments
- Used GDX-GLTF library for rendering and loading GLTF model file
- Car Model downloaded from TurboSquid created by Raphael Gonçalves (Rgsdev) under CC0 License.  Public domain and free to use on any project, even commercial.

