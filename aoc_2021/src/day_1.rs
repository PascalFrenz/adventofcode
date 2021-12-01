pub fn task_a(input: &str) -> usize {
    let numbers = parse_input(input);
    return count_increases(numbers);
}

pub fn task_b(input: &str) -> usize {
    let numbers = parse_input(input);
    let window_sums = join_values_into_windows(numbers);
    return count_increases(window_sums);
}

fn parse_input(input: &str) -> Vec<usize> {
    input.lines()
        .map(|s| s.parse().expect("could not parse number"))
        .collect::<Vec<usize>>()
}

fn join_values_into_windows(numbers: Vec<usize>) -> Vec<usize> {
    let mut windows: Vec<[usize; 3]> = vec![];
    for i in 2..numbers.len() {
        windows.push([numbers[i - 2], numbers[i - 1], numbers[i]]);
    }
    let sums: Vec<usize> = windows.iter()
        .map(|arr| arr.iter().fold(0, |a, b| a + b))
        .collect::<Vec<usize>>();
    sums
}

fn count_increases(numbers: Vec<usize>) -> usize {
    let mut increases: usize = 0;
    for i in 1..numbers.len() {
        let prev = numbers[i - 1];
        let curr = numbers[i];
        increases += if curr > prev { 1 } else { 0 };
    }
    return increases;
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_task_a_example() {
        let input = "199\n200\n208\n210\n200\n207\n240\n269\n260\n263";
        assert_eq!(task_a(input), 7);
    }

    #[test]
    fn test_task_a_only_one() {
        let input = "199";
        assert_eq!(task_a(input), 0);
    }

    #[test]
    fn test_task_a_empty_input() {
        let input = "";
        assert_eq!(task_a(input), 0);
    }

    #[test]
    fn test_task_b_example() {
        let input = "199\n200\n208\n210\n200\n207\n240\n269\n260\n263";
        assert_eq!(task_b(input), 5);
    }

    #[test]
    fn test_task_b_only_one() {
        let input = "199";
        assert_eq!(task_b(input), 0);
    }

    #[test]
    fn test_task_b_empty_input() {
        let input = "";
        assert_eq!(task_b(input), 0);
    }


}