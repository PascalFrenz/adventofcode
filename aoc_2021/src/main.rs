use std::{fs};
use std::collections::HashMap;
use std::time::Instant;

use crate::practice::{FerryTerminal, FerryTerminalState};
use crate::util::read_input_file;

mod practice;
mod util;

fn main() {
    task_10();
    task_11();
}

fn task_10() {
    let start = Instant::now();
    let filename = "resources/input10.txt";
    println!("...reading contents of: {}", filename);
    let contents = fs::read_to_string(filename).expect("Something went wrong while reading the file");
    let done_reading_file = Instant::now();
    println!("done! took {}ns", done_reading_file.duration_since(start).as_nanos());
    println!("...parsing content into number vector");
    let mut numbers: Vec<i32> = parse_to_vector(contents);
    append_outlet_and_built_in_charger(&mut numbers);
    sort_asc(&mut numbers);
    let done_parsing_numbers = Instant::now();
    println!("done! took {}ns", done_parsing_numbers.duration_since(done_reading_file).as_nanos());
    println!("...calculating occurrences");
    let diff_occurrences = calc_diff_occurrences(&mut numbers);
    let done_calculating = Instant::now();
    println!("done! took {}ns", done_calculating.duration_since(done_parsing_numbers).as_nanos());
    println!("Occurrences of differences: {:?}", diff_occurrences);
    let result = diff_occurrences.get(&1).unwrap() * diff_occurrences.get(&3).unwrap();
    let done_all = Instant::now();
    println!("Result: {}", result);
    println!();
    println!("execution time overall: {}ms, {}ns", done_all.duration_since(start).as_millis(), done_all.duration_since(start).as_nanos());
}

fn calc_diff_occurrences(numbers: &mut Vec<i32>) -> HashMap<i32, i32> {
    let mut diff_occurrences: HashMap<i32, i32> = HashMap::new();
    for i in 0..numbers.len() - 1 {
        let curr = numbers.get(i).expect(format!("could not get number at idx {}", i).as_str());
        let next = numbers.get(i + 1).expect(format!("could not get number at idx {}", i).as_str());
        let diff = next - curr;
        diff_occurrences.insert(diff, diff_occurrences.get(&diff).unwrap_or_else(|| &0) + 1);
    }
    diff_occurrences
}

fn sort_asc(numbers: &mut Vec<i32>) {
    numbers.sort();
}

fn append_outlet_and_built_in_charger(numbers: &mut Vec<i32>) {
    numbers.append(&mut vec![0]);
    numbers.append(&mut vec![numbers.last().unwrap() + 3]);
}

fn parse_to_vector(contents: String) -> Vec<i32> {
    contents.lines().map(|s| s.parse().unwrap()).collect::<Vec<i32>>()
}

fn task_11() {
    let start = Instant::now();
    let input = read_input_file("resources/input11.txt");
    let result_state = calculate_stable_state(&input);
    println!("Result: {} occupied seats", result_state.occupied_seats());

    let end = Instant::now();
    println!("took {:?}ms", end.duration_since(start).as_millis());
}

fn calculate_stable_state(input: &String) -> FerryTerminal {
    let mut iterations = 0;
    let mut current_state = FerryTerminal::new(&input);
    let mut state_changed = true;
    while state_changed {
        let next_state = current_state.compute_next_state();
        state_changed = current_state.occupied_seats() != next_state.occupied_seats();
        current_state = if state_changed { next_state } else { current_state };
        iterations += 1;
    }
    println!("DONE! took {} iterations", iterations);

    return current_state;
}
