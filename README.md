# BonbonBash-Slick2D

Candy Crush Saga clone reproduced with the Lightweight Java Game Library (LWJGL) and Slick2D

## LWJGL and Slick2D Installation Instructions for the NetBeans IDE

1. Download the latest LWJGL release from https://github.com/LWJGL/lwjgl3/releases.
2. Download the latest Slick2D release from http://slick.ninjacave.com/.
3. Unzip the downloads and copy the folders to a directory of your choice.
4. In NetBeans under Tools->Libraries->New Library:
    1. Name a new library "slick".
    2. Choose add .jar file.
    3. Select from the folder slick/lib and choose at least the following:
    ```
    jinput.jar
    lwjgl.jar
    lwjgl_util.jar
    slick.jar
    jogg-0.0.7.jar
    jorbis-0.0.15.jar 
    ```
5. In NetBeans right click on project, select Properties->Libraries->Add library and choose slick.
6. In NetBeans select RUN and change VM to:
    >-Djava.library.path=C:\\...\lwjgl\lwjgl-2.9.1\native\windows

7. Compile and enjoy!

## Contact
If you have any questions or concerns, please feel free to send me an email at zy6zhou@edu.uwaterloo.ca.
