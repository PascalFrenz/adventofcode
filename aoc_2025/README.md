# Advent Of Code 2025

This is where all the tasks are for the AoC 2025!

Language is Java 25.

## How to set up the `.env`

Create a new `.env` file in this repository like so:

```dotenv
SESSION=<YOUR_SESSION_COOKIE>
```

Get the session cookie from [Advent Of Code](https://adventofcode.com/) by looking into the
developer tools (F12) > Application > Cookies > `session`

## How to generate a day

Simply execute the following in the root of this directory:
```shell
.\gradlew.bat -Pday="01" :generateDay
```

Now, get the input from the website by executing `fetch-input.sh`

```shell
./fetch-input.sh
```


