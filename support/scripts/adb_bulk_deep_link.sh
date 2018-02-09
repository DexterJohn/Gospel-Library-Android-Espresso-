# Open a given deep link on all connected Android devices
#
# cd [location of script]
# ./adb_bulk_deep_link.sh gospellibrary://content/scriptures/bofm/1-ne/3?lang=eng

for SERIAL in $(adb devices | tail -n +2 | cut -sf 1);
    do
        echo "Launching deep link on $SERIAL"
        adb -s $SERIAL shell am start -W -a android.intent.action.VIEW -d "$1"
    done
