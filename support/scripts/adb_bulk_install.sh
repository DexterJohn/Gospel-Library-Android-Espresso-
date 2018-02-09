# Install a downloaded apk to all connected Android devices (or update, if already installed)
#
# cd [location of script]
# ./adb_bulk_install.sh ~/Downloads/gospel-library-alpha-20170825-1624.apk

for SERIAL in $(adb devices | tail -n +2 | cut -sf 1);
    do
        echo "Installing $1 on $SERIAL"
        adb -s $SERIAL install -r $1
        adb -s $SERIAL shell am start -n org.lds.ldssa.dev/org.lds.ldssa.ui.activity.StartupActivity
    done
