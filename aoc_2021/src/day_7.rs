use std::collections::HashMap;
use std::ops::Sub;

pub fn task_a(input: &str) -> u64 {
    let numbers = parse_input(input);
    let mode = mode(&numbers);
    let fuel_differences = calc_fuel_diffs(&numbers, mode);

    fuel_differences.iter().sum()
}

fn calc_fuel_diffs(numbers: &Vec<u64>, mode: u64) -> Vec<u64> {
    numbers.iter()
        .clone()
        .map(|n| if mode >= *n { mode - *n } else { *n - mode })
        .collect::<Vec<u64>>()
}

pub fn task_b(input: &str) -> u64 {
    return 0;
}

fn parse_input(input: &str) -> Vec<u64> {
    input.trim().split(",").map(|n| n.parse().unwrap()).collect()
}

fn mode(numbers: &[u64]) -> u64 {
    let mut counts = HashMap::new();

    numbers.iter().copied().max_by_key(|&n| {
        let count = counts.entry(n).or_insert(0);
        *count += 1;
        *count
    }).expect("could not calculate mode")
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_a_example() {
        let input = "16,1,2,0,4,2,7,1,2,14";
        assert_eq!(37, task_a(input));
    }
}