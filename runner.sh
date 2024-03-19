#!/bin/bash
#----------------------------------------------------------------
# This script expects the following environment variables
#
#    HUB_HOST
#    BROWSER
#    THREAD_COUNT
#    TEST_SUITE
#----------------------------------------------------------------
# Let's print what we received
  echo "-----------------------------------------------------------"
  echo "HUB_HOST              :   ${HUB_HOST:-selenium-hub}"
  echo "BROWSER               :   ${BROWSER:-chrome}"
  echo "THREAD_COUNT          :   ${THREAD_COUNT}"
  echo "TEST_SUITE            :   ${TEST_SUITE}"
  echo "-----------------------------------------------------------"
  # Do not start the test immediately. HUb has to ready with browser nodes
  echo "Checking if Hub is ready...!"
  count=0
  while [ "$(curl -s http://${HUB_HOST:-selenium-hub}:4444/status | jq -r .value.ready )" != "true" ]
  do
    count=$((count+1))
    echo "Attempt: ${count}"
    if [ "$count" -ge 30 ];
    then
      echo "***** HUB_HOST This NOT READY WITHIN 30 SECONDS *****"
      exit 1
    fi
    sleep 1
  done

  # At this point, selenium grid should be up!

  echo "Selenium Grid is up and running. Running the test....."

  # Start the java command

  java -cp 'libs/*' \
       -Dselenium.grid.enable=true \
       -Dselenium.grid.hubHost="${HUB_HOST:-selenium-hub}" \
       -DbrowserName="${BROWSER:-chrome}" \
       org.testng.TestNG \
       -threadcount "${THREAD_COUNT:-1}" \
        test-suites/"${TEST_SUITE}"