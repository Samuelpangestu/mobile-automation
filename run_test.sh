#!/bin/bash

##############################################
# Mobile Automation Test Runner
# Usage: ./run_test.sh [platform] [tag]
##############################################

# Colors
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo ""
echo -e "${BLUE}=========================================="
echo "   Mobile Automation Test Runner"
echo -e "==========================================${NC}"
echo ""

# Parse arguments
PLATFORM=${1:-android}
TAG=${2:-}

# Validate platform
if [[ "$PLATFORM" != "android" && "$PLATFORM" != "ios" ]]; then
    echo -e "${RED}Error: Platform must be 'android' or 'ios'${NC}"
    echo "Usage: ./run_test.sh [platform] [tag]"
    echo "Example:"
    echo "  ./run_test.sh android"
    echo "  ./run_test.sh android @smoke"
    echo "  ./run_test.sh ios @login"
    exit 1
fi

# Display configuration
echo -e "${GREEN}Configuration:${NC}"
echo "  Platform: $PLATFORM"
if [ -n "$TAG" ]; then
    echo "  Tag: $TAG"
else
    echo "  Tag: all tests"
fi
echo ""

# Check prerequisites
echo -e "${YELLOW}Checking prerequisites...${NC}"

if ! command -v java &> /dev/null; then
    echo -e "${RED}Error: Java not found${NC}"
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Error: Maven not found${NC}"
    exit 1
fi

if ! command -v appium &> /dev/null; then
    echo -e "${RED}Error: Appium not found${NC}"
    echo "Install: npm install -g appium"
    exit 1
fi

echo -e "${GREEN}✓ All prerequisites met${NC}"
echo ""

# Check Appium server
echo -e "${YELLOW}Checking Appium server...${NC}"
if lsof -Pi :4723 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo -e "${GREEN}✓ Appium server is running${NC}"
else
    echo -e "${YELLOW}Starting Appium server...${NC}"
    appium &> appium.log &
    APPIUM_PID=$!
    sleep 5

    if lsof -Pi :4723 -sTCP:LISTEN -t >/dev/null 2>&1; then
        echo -e "${GREEN}✓ Appium server started (PID: $APPIUM_PID)${NC}"
    else
        echo -e "${RED}Error: Failed to start Appium${NC}"
        echo "Check appium.log for details"
        exit 1
    fi
fi
echo ""

# Check device/emulator
echo -e "${YELLOW}Checking device...${NC}"
if [ "$PLATFORM" == "android" ]; then
    DEVICE_COUNT=$(adb devices | grep -v "List" | grep "device" | wc -l)
    if [ $DEVICE_COUNT -eq 0 ]; then
        echo -e "${RED}Error: No Android device/emulator found${NC}"
        echo "Start emulator or connect device"
        exit 1
    fi
    echo -e "${GREEN}✓ Android device found${NC}"
fi
echo ""

# Clean previous results
echo -e "${YELLOW}Cleaning previous results...${NC}"
mvn clean -q
echo -e "${GREEN}✓ Cleaned${NC}"
echo ""

# Build Maven command
MVN_CMD="mvn test -Dplatform=$PLATFORM"

if [ -n "$TAG" ]; then
    MVN_CMD="$MVN_CMD -Dcucumber.filter.tags=\"$TAG\""
fi

# Run tests
echo -e "${BLUE}=========================================="
echo "   Running Tests"
echo -e "==========================================${NC}"
echo ""

eval $MVN_CMD
TEST_RESULT=$?

echo ""
echo -e "${BLUE}=========================================="
if [ $TEST_RESULT -eq 0 ]; then
    echo -e "   ${GREEN}Tests Completed${NC}"
else
    echo -e "   ${RED}Tests Failed${NC}"
fi
echo -e "${BLUE}==========================================${NC}"
echo ""

# Generate and serve Allure report
if [ -d "target/allure-results" ] && [ "$(ls -A target/allure-results)" ]; then
    echo -e "${YELLOW}Opening Allure report...${NC}"
    echo ""

    # Use mvn allure:serve to automatically generate and open report
    mvn allure:serve

else
    echo -e "${YELLOW}No test results found${NC}"
fi

echo ""

# Cleanup - Stop Appium if we started it
if [ ! -z "$APPIUM_PID" ]; then
    echo -e "${YELLOW}Stopping Appium server...${NC}"
    kill $APPIUM_PID 2>/dev/null
    echo -e "${GREEN}✓ Stopped${NC}"
fi

echo ""
echo -e "${BLUE}=========================================="
echo "   Done"
echo -e "==========================================${NC}"
echo ""

# Show available tags
if [ $TEST_RESULT -ne 0 ]; then
    echo "Available tags:"
    echo "  @smoke    - Smoke tests"
    echo "  @login    - Login tests"
    echo "  @dashboard - Dashboard tests"
    echo "  @product  - Product tests"
    echo ""
    echo "Example: ./run_test.sh android @smoke"
    echo ""
fi

exit $TEST_RESULT
