import time
import random

def show_boot_logs():
    logs = [
        "Initializing kernel modules...",
        "Setting default values for core params",
        "Loading device drivers...",
        "Mounting virtual file systems...",
        "Enabling USB Controller...",
        "Detected CPU: Intel Virtual CPU @ 2.90GHz",
        "Detected Memory: 4GB",
        "Starting udev daemon...",
        "Assigning network interfaces...",
        "Starting TTY session..."
    ]
    for i, log in enumerate(logs):
        time.sleep(0.2 + random.uniform(0, 0.3))
        print(f"[{i+1:04d}] {log}")