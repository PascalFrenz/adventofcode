use std::fmt::Debug;

mod practice_2020;
mod util;
mod day_1;
mod day_2;
mod day_3;
mod day_4;

fn main() {
    execute_day(1, day_1::task_a, day_1::task_b);
    execute_day(2, day_2::task_a, day_2::task_b);
    execute_day(3, day_3::task_a, day_3::task_b);
    execute_day(4, day_4::task_a, day_4::task_b);
}

fn execute_day<T>(day: usize, a: fn(&str) -> T, b: fn(&str) -> T) where T: Debug {
    let input = util::read_input_file(&format!("resources/input{:?}.txt", day));
    println!("Result for day {:?}, task a: {:?}", day, a(&input));
    println!("Result for day {:?}, task b: {:?}", day, b(&input));
}


