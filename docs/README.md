# User Guide

## Features 

### Add a ToDo: `todo`
Adds a ToDo to the task list.

Format: `todo DESCRIPTION`

Examples:
* `todo wash the toilet`
* `todo sleep`

### Adds an Event: `event`
Adds an Event to the task list.

Format: `event DESCRIPTION /DATE_TIME<yyyy-mm-dd, HH-mm>`

Example:
* `event reunion /2012-12-12, 23:59`

### Adds a Deadline: `deadline`
Adds a Deadline to the task list.

Format: `deadline DESCRIPTION /DATE_TIME<yyyy-mm-dd, HH-mm>`

Example:
* `event cut cake /2012-12-12, 23:59`

### List tasks: `list`
List all tasks in task list.

Format: `list`

### Filter tasks by word: `find`
Find all tasks that contains user-provided keyword.

Format: `find KEYWORD`

Examples:
* `find cake`
* `find study`

### Delete a task: `delete`
Delete a task with user-provided index.

Format: `delete INDEX`

Example:
* `delete 1`

### Marks task as done: `done`
Changes a task's status to done

Format: `done INDEX`

Example:
* `done 2`

### Display help page: `help`
Prints out help page that displays available user commands 

Format: `help`

### Exit the program: `bye`
Exits the program

Format: `bye`

## Command Summary
Command | Format, Example
------- | --------------
todo | `todo DESCRIPTION`, e.g.`todo study`
deadline | `deadline DESCRIPTION /DATE_TIME`, e.g. `deadline clean toilet /2012-12-12, 23:59`
event | `event DESCRIPTION /DATE_TIME`, e.g. `event reunion /2012-12-12, 23:59`
list | `list`
find | `find KEYWORD`, e.g. `find bear`
delete | `delete INDEX`, e.g. `delete 2`
done | `done INDEX`, e.g. `done 2`
help | `help`
bye | `bye`






