import psutil
import time
import os

def show_monitor():
    try:
        while True:
            os.system('clear')  # Use 'cls' on Windows if testing locally
            print("=== SYSTEM MONITOR ===\n")
            print(f"CPU Usage: {psutil.cpu_percent()}%")
            print(f"Memory Usage: {psutil.virtual_memory().percent}%")
            print(f"Disk Usage: {psutil.disk_usage('/').percent}%")
            print(f"Processes: {len(psutil.pids())}")
            time.sleep(1)
    except KeyboardInterrupt:
        print("\nExiting system monitor.")