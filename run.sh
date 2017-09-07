#!/bin/bash
gradle installDebug -x lint --build-cache --daemon --parallel --offline --configure-on-demand && \
adb shell am start com.malin.decode.bitmap/.activity.MainActivity

