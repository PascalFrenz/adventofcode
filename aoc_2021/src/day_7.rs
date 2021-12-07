pub fn task_a(input: &str) -> u64 {
    let numbers = parse_input(input);
    let median = median(&mut numbers.clone());
    let fuel_differences = calc_fuel_diffs(&numbers, median);

    fuel_differences.iter().sum()
}

pub fn task_b(input: &str) -> u64 {
    let numbers = parse_input(input);
    let avg = average(&mut numbers.clone());
    let fuel_differences = numbers.clone().iter()
        .map(|&n| {
            let gauss_sum: fn(u64) -> u64 = |n| n * (n + 1) / 2;
            if avg >= n { gauss_sum(avg - n) } else { gauss_sum(n - avg) }
        })
        .reduce(|acc, n| acc + n )
        .unwrap();
    return fuel_differences;
}

fn calc_fuel_diffs(numbers: &Vec<u64>, mode: u64) -> Vec<u64> {
    numbers.iter()
        .clone()
        .map(|n| if mode >= *n { mode - *n } else { *n - mode })
        .collect::<Vec<u64>>()
}

fn parse_input(input: &str) -> Vec<u64> {
    input.trim().split(",").map(|n| n.parse().unwrap()).collect()
}

fn median(numbers: &mut Vec<u64>) -> u64 {
    numbers.sort();
    let mid = numbers.len() / 2;
    numbers[mid]
}

fn average(numbers: &Vec<u64>) -> u64 {
    numbers.iter().sum::<u64>() / u64::try_from(numbers.len()).unwrap()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_a_example() {
        let input = "16,1,2,0,4,2,7,1,2,14";
        assert_eq!(37, task_a(input));
    }

    #[test]
    fn test_b_example() {
        let input = "16,1,2,0,4,2,7,1,2,14";
        assert_eq!(168, task_b(input));
    }
}