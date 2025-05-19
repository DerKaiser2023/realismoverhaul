from rich.console import Console
from rich.table import Table
from rich.live import Live
import psutil
import time

console = Console()

def get_stats_table():
    table = Table(title="🖥️ System Monitor")

    table.add_column("Metric", justify="left", style="cyan", no_wrap=True)
    table.add_column("Value", justify="right", style="magenta")

    table.add_row("CPU Usage", f"{psutil.cpu_percent()}%")
    table.add_row("Memory Usage", f"{psutil.virtual_memory().percent}%")
    table.add_row("Disk Usage", f"{psutil.disk_usage('/').percent}%")
    table.add_row("Processes", str(len(psutil.pids())))

    return table

def show_monitor():
    with Live(console=console, auto_refresh=False) as live:
        try:
            while True:
                table = get_stats_table()
                live.update(table, refresh=True)
                time.sleep(1)
        except KeyboardInterrupt:
            console.print("\n[bold red]Exiting System Monitor...[/bold red]")