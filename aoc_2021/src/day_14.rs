use std::borrow::Borrow;
use std::collections::{BTreeSet, HashMap, HashSet};
use std::iter::Map;
use std::ops::Div;
use std::str;
use std::time::Instant;

type Position = usize;
type Polymer = String;

type Ruleset = HashMap<Polymer, String>;
type Positions = HashSet<Position>;
type PolymerState = String;

pub fn task_a(input: &str) -> usize {
    let init_template = parse_template(input);
    let mut occurrences = to_pair_map(init_template);
    let ruleset = parse_ruleset(input);
    for _ in 0..10 {
        occurrences = do_one_pass(&mut occurrences, &ruleset);
    }
    let counts = count_letters(occurrences);
    let max = *counts.values().max().unwrap();
    let min = *counts.values().min().unwrap();
    (max - min).div(2)
}

pub fn task_b(input: &str) -> usize {
    let init_template = parse_template(input);
    let mut occurrences = to_pair_map(init_template);
    let ruleset = parse_ruleset(input);
    for _ in 0..40 {
        occurrences = do_one_pass(&mut occurrences, &ruleset);
    }
    let counts = count_letters(occurrences);
    let max = *counts.values().max().unwrap();
    let min = *counts.values().min().unwrap();
    (max - min).div(2)
}

fn do_one_pass(occurrences: &mut HashMap<String, usize>, ruleset: &HashMap<String, String>) -> HashMap<String, usize> {
    let mut new_occ: HashMap<String, usize> = HashMap::new();
    for (pair, number) in occurrences.iter() {
        let middle = ruleset.get(&*pair).expect(&*format!("Pair not in ruleset! {}", &*pair));
        let (left, right) = pair.split_at(1);
        let new_key_left = format!("{}{}", left, middle);
        let new_left = new_occ.entry(new_key_left).or_insert(0);
        *new_left += number;
        let new_key_right = format!("{}{}", middle, right);
        let new_right = new_occ.entry(new_key_right).or_insert(0);
        *new_right += number;
    }
    new_occ
}

fn count_letters(occurrences: HashMap<String, usize>) -> HashMap<String, usize> {
    let mut count: HashMap<String, usize> = HashMap::new();
    for (pair, number) in occurrences {
        let (left, right) = pair.split_at(1);
        let left_count = count.entry(String::from(left)).or_insert(0);
        *left_count += number;
        let right_count = count.entry(String::from(right)).or_insert(0);
        *right_count += number;
    }

    count
}

fn to_pair_map(template: &str) -> HashMap<String, usize> {
    let mut result = HashMap::new();
    let mut window: Vec<(Position, char)> = vec![];
    for (pos, char) in template.char_indices() {
        if window.len() < 2 {
            window.push((pos, char))
        }

        if window.len() == 2 {
            let first = window[0].1;
            let second = window[1].1;

            let pair = format!("{}{}", first, second);
            let count = result.entry(pair).or_insert(0);
            *count += 1;

            window.remove(0);
        }
    }
    return result;
}

fn parse_template(input: &str) -> &str {
    input.lines().nth(0).expect("Could not get template")
}

fn parse_ruleset(input: &str) -> Ruleset {
    input.lines()
        .skip(2)
        .map(|line| {
            let mut split = line.split(" -> ").collect::<Vec<_>>();
            (split[0], split[1])
        })
        .fold(HashMap::new(), |mut acc, (key, val)| {
            acc.insert(Polymer::from(key), String::from(val));
            acc
        })
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_example_a() {
        let input = "NNCB\n\nCH -> B\nHH -> N\nCB -> H\nNH -> C\nHB -> C\nHC -> B\nHN -> C\nNN -> C\nBH -> H\nNC -> B\nNB -> B\nBN -> B\nBB -> N\nBC -> B\nCC -> N\nCN -> C";
        assert_eq!(1588, task_a(input));
    }

    #[test]
    fn test_example_b() {
        let input = "NNCB\n\nCH -> B\nHH -> N\nCB -> H\nNH -> C\nHB -> C\nHC -> B\nHN -> C\nNN -> C\nBH -> H\nNC -> B\nNB -> B\nBN -> B\nBB -> N\nBC -> B\nCC -> N\nCN -> C";
        let actual = task_b(input);
        assert_eq!(2188189693529, actual);
    }
}