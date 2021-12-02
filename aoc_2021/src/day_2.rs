use std::ops::Neg;

pub fn task_a(input: &str) -> i128 {
    return if input.is_empty() {
        0
    } else {
        calc_result(
            input,
            (0, 0),
            |a, b| (a.0 + b.0, a.1 + b.1),
            |r| r.0 * r.1
        )
    };
}

pub fn task_b(input: &str) -> i128 {
    return if input.is_empty() {
        0
    } else {
        calc_result(
            input,
            (0, 0, 0),
            |a, b| {
                let aim = a.0 + b.0;
                let depth = if b.1.eq(&0) { a.1 } else { a.1 + a.0 * b.1 };
                let horizontal_pos = a.2 + b.1;
                return (aim, depth, horizontal_pos);
            },
            |r| r.1 * r.2
        )
    };
}

fn calc_result<T, R>(input: &str, init: R, acc_fn: fn(R, (i128, i128)) -> R, res_fn: fn(R) -> T) -> T {
    let final_position = input.lines()
        .map(|x| map_movement_to_tuple(x))
        .fold(init, acc_fn);
    res_fn(final_position)
}

fn map_movement_to_tuple(x: &str) -> (i128, i128) {
    let mut split = x.split_whitespace();
    let action = split.next().expect("could not parse action");
    let movement = split.last().map(|s| s.parse::<i128>().expect("could not parse movement")).unwrap();
    return if "forward".eq(action) {
        (0, movement)
    } else if x.contains("down") {
        (movement, 0)
    } else if x.contains("up") {
        (movement.neg(), 0)
    } else {
        (0, 0)
    };
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_task_a_example() {
        let input = "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2";
        assert_eq!(task_a(input), 150)
    }

    #[test]
    fn test_task_b_example() {
        let input = "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2";
        assert_eq!(task_b(input), 900)
    }
}