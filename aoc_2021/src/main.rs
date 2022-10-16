use std::fmt::Debug;
use std::fs;
use std::time::Instant;

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
mod day_12;
mod day_13;
mod day_14;
mod day_15;
mod day_16;
mod day_17;
mod day_18;
mod day_19;
mod day_20;
mod day_21;
mod day_22;
mod day_23;
mod day_24;

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
    execute_day(12, day_12::task_a, day_12::task_b);
    execute_day(13, day_13::task_a, day_13::task_b);
    execute_day(14, day_14::task_a, day_14::task_b);
    execute_day(15, day_15::task_a, day_15::task_b);
    execute_day(16, day_16::task_a, day_16::task_b);
    execute_day(17, day_17::task_a, day_17::task_b);
    execute_day(18, day_18::task_a, day_18::task_b);
    execute_day(19, day_19::task_a, day_19::task_b);
    execute_day(20, day_20::task_a, day_20::task_b);
    execute_day(21, day_21::task_a, day_21::task_b);
    execute_day(22, day_22::task_a, day_22::task_b);
    execute_day(23, day_23::task_a, day_23::task_b);
    execute_day(24, day_24::task_a, day_24::task_b);
    let end = Instant::now();
    eprintln!("duration = {:#?}", end.duration_since(start));
}

fn execute_day<T, R>(day: usize, a: fn(&str) -> T, b: fn(&str) -> R) where T: Debug, R: Debug {
    if let Some(input) = fs::read_to_string(&format!("resources/input{:?}.txt", day)).ok() {
        println!("Result for day {:?}\n\ttask a: {:?}\n\ttask b: {:?}", day, a(input.clone().trim()), b(input.clone().trim()));
    } else {
        println!("Input for day {:?} was not present, skipping...", day);
    }
}


