# Advent Of Code 2022

This is where all the tasks are for the AoC 2022!

Language is Kotlin 2.

## How to set up the `.env`

Create a new `.env` file in this repository like so:

```dotenv
SESSION=<YOUR_SESSION_COOKIE>
```

Get the session cookie from [Advent Of Code](https://adventofcode.com/) by looking into the
developer tools (F12) > Application > Cookies > `session`

Now, get the input from the website by executing `fetch-input.sh`

```shell
# first parameter is the day. Here: 1
./fetch-input.sh 1
```
