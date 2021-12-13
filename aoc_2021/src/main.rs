use std::fmt::Debug;
use std::time::Instant;

mod practice_2020;
mod util;
mod day_1;
mod day_2;
mod day_3;
mod day_4;
mod day_5;
mod day_6;
mod day_7;
mod day_8;
mod day_9;
mod day_10;
mod day_11;
mod day_13;

fn main() {
    let start = Instant::now();
    execute_day(1, day_1::task_a, day_1::task_b);
    execute_day(2, day_2::task_a, day_2::task_b);
    execute_day(3, day_3::task_a, day_3::task_b);
    execute_day(4, day_4::task_a, day_4::task_b);
    execute_day(5, day_5::task_a, day_5::task_b);
    execute_day(6, day_6::task_a, day_6::task_b);
    execute_day(7, day_7::task_a, day_7::task_b);
    execute_day(8, day_8::task_a, day_8::task_b);
    execute_day(9, day_9::task_a, day_9::task_b);
    execute_day(10, day_10::task_a, day_10::task_b);
    execute_day(11, day_11::task_a, day_11::task_b);
    execute_day(13, day_13::task_a, day_13::task_b);
    let end = Instant::now();
    eprintln!("duration = {:#?}", end.duration_since(start));
}

fn execute_day<T, R>(day: usize, a: fn(&str) -> T, b: fn(&str) -> R) where T: Debug, R: Debug {
    let input = util::read_input_file(&format!("resources/input{:?}.txt", day));
    println!("Result for day {:?}, task a: {:?}", day, a(input.trim()));
    println!("Result for day {:?}, task b: {:?}", day, b(input.trim()));
}


