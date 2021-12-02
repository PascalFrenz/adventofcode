mod practice_2020;
mod util;
mod day_1;
mod day_2;

fn main() {
    let input_1 = util::read_input_file("resources/input1.txt");
    println!("Result for day 1, task a: {:?}", day_1::task_a(&input_1));
    println!("Result for day 1, task b: {:?}", day_1::task_b(&input_1));
    let input_2 = util::read_input_file("resources/input2.txt");
    println!("Result for day 2, task a: {:?}", day_2::task_a(&input_2));
    println!("Result for day 2, task b: {:?}", day_2::task_b(&input_2));
}


