mod practice_2020;
mod util;
mod day_1;

fn main() {
    let input = util::read_input_file("resources/input1.txt");
    println!("Result for day 1, task a: {:?}", day_1::task_a(&input));
    println!("Result for day 1, task b: {:?}", day_1::task_b(&input));
}


