# Uninstall a given app (by package name) from all connected Android devices
#
# cd [location of script]
# ./adb_bulk_uninstall.sh org.lds.ldssa.dev

for SERIAL in $(adb devices | tail -n +2 | cut -sf 1);
    do
        echo "Unistalling $1 from $SERIAL"
        adb -s $SERIAL uninstall $1
    done
