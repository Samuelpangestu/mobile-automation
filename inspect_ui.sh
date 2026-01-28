#!/bin/bash

echo "=== Inspecting SauceDemo Mobile UI ==="
echo ""

# Launch app
echo "1. Launching app..."
adb shell am start -n com.saucelabs.mydemoapp.android/.view.activities.SplashActivity
sleep 5

# Dump initial screen
echo "2. Dumping initial screen..."
adb shell uiautomator dump /sdcard/screen1.xml
adb pull /sdcard/screen1.xml /tmp/screen1.xml 2>/dev/null

echo ""
echo "=== INITIAL SCREEN - Resource IDs ==="
grep -o 'resource-id="com.saucelabs[^"]*"' /tmp/screen1.xml | sed 's/resource-id="com.saucelabs.mydemoapp.android:id\///' | sed 's/"//' | sort -u
echo ""

echo "=== INITIAL SCREEN - Content Descriptions ==="
grep -o 'content-desc="[^"]*"' /tmp/screen1.xml | sed 's/content-desc="//' | sed 's/"//' | grep -v "^$" | sort -u
echo ""

echo "=== INITIAL SCREEN - Text Values ==="
grep -o 'text="[^"]*"' /tmp/screen1.xml | sed 's/text="//' | sed 's/"//' | grep -v "^$" | sort -u
echo ""

# Click menu
echo "3. Clicking menu button..."
adb shell input tap 100 100  # Top left corner where menu usually is
sleep 2

# Dump menu screen
echo "4. Dumping menu screen..."
adb shell uiautomator dump /sdcard/screen2.xml
adb pull /sdcard/screen2.xml /tmp/screen2.xml 2>/dev/null

echo ""
echo "=== MENU SCREEN - Text Values ==="
grep -o 'text="[^"]*"' /tmp/screen2.xml | sed 's/text="//' | sed 's/"//' | grep -v "^$" | sort -u
echo ""

echo "UI inspection complete. XML files saved to /tmp/screen*.xml"
