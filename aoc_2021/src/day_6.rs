use std::collections::HashMap;
use std::ops::Add;

type FishCount = u64;
type Day = u8;
type FishMap = HashMap<Day, FishCount>;

pub fn task_a(input: &str) -> FishCount {
    let mut lanternfishs = parse_input(input);

    for _ in 0..80 {
        lanternfishs = calc_next_gen(lanternfishs);
    }

    return lanternfishs.iter().fold(0, |sum, (_, &v)| sum + v);
}

pub fn task_b(input: &str) -> FishCount {
    let mut lanternfishs = parse_input(input);

    for _ in 0..256 {
        lanternfishs = calc_next_gen(lanternfishs);
    }
    return lanternfishs.iter().fold(0, |sum, (_, &v)| sum + v);
}

fn calc_next_gen(lanternfishs: FishMap) -> FishMap {
    return HashMap::from([
        (0, *lanternfishs.get(&1).unwrap()),
        (1, *lanternfishs.get(&2).unwrap()),
        (2, *lanternfishs.get(&3).unwrap()),
        (3, *lanternfishs.get(&4).unwrap()),
        (4, *lanternfishs.get(&5).unwrap()),
        (5, *lanternfishs.get(&6).unwrap()),
        (6, lanternfishs.get(&7).unwrap() + lanternfishs.get(&0).unwrap()),
        (7, *lanternfishs.get(&8).unwrap()),
        (8, *lanternfishs.get(&0).unwrap()),
    ]);
}

fn parse_input(input: &str) -> FishMap {
    let init_hashmap: FishMap = HashMap::from([(0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0), (8, 0)]);

    return input.trim()
        .split(",")
        .map(|s| s.parse().unwrap())
        .fold(init_hashmap, |mut acc, n| {
            acc.insert(n, acc.get(&n).unwrap().add(1));
            return acc;
        });
}

#[cfg(test)]
mod tests {

    use super::*;

    #[test]
    fn test_task_a_example() {
        let input = "3,4,3,1,2";
        assert_eq!(5934, task_a(input))
    }

    #[test]
    fn test_task_b_example() {
        let input = "3,4,3,1,2";
        assert_eq!(26984457539, task_b(input))
    }
}