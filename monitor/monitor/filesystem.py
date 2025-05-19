from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from monitor.config import CODESPACE_PATH
from monitor.logger import log_info
import time

class FileChangeHandler(FileSystemEventHandler):
    def on_modified(self, event):
        log_info(f"[File Modified] {event.src_path}")

    def on_created(self, event):
        log_info(f"[File Created] {event.src_path}")

    def on_deleted(self, event):
        log_info(f"[File Deleted] {event.src_path}")

def start_file_watch():
    event_handler = FileChangeHandler()
    observer = Observer()
    observer.schedule(event_handler, path=CODESPACE_PATH, recursive=True)
    observer.start()
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
        observer.join()