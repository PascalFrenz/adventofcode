source .env

day=$1; shift;
fileName="";

if [ "$day" -lt 10 ]; then
    fileName="day0$day.txt";
else
    fileName="day$day.txt";
fi

curl -b "session=$SESSION" "https://adventofcode.com/2023/day/$day/input" > "src/main/resources/$fileName";
