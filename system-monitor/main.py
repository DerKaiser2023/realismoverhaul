import time
from boot_logs import show_boot_logs
from monitor import show_monitor

def main():
    show_boot_logs()
    print("\nSystem Monitor Booted. Starting Live Monitoring...\n")
    show_monitor()

if __name__ == "__main__":
    main()