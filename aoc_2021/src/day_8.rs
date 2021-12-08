use std::collections::{HashMap, HashSet};

pub fn task_a(input: &str) -> usize {
    let unique_digits: [usize; 4] = [2, 4, 3, 7];
    return input.lines()
        .map(|line| line.split(" | ").last().unwrap())
        .map(|output| output.split(" ").collect::<Vec<&str>>())
        .flatten()
        .map(|x| x.len())
        .filter(|str_len| unique_digits.contains(str_len))
        .count();
}

pub fn task_b(input: &str) -> usize {
    input.lines()
        .map(|line| {
            let mut split = line.split(" | ");
            let parts = (
                split.next().map(|x| x.split(" ").collect::<Vec<&str>>()).unwrap(),
                split.last().map(|x| x.split(" ").collect::<Vec<&str>>()).unwrap()
            );
            parts
        })
        .map(|(notes, number_strs)| {
            let mut numbers: HashMap<u8, &str> = HashMap::new();
            for note in notes.clone() {
                if note.len() == 2 {
                    numbers.insert(1, note);
                } else if note.len() == 4 {
                    numbers.insert(4, note);
                } else if note.len() == 3 {
                    numbers.insert(7, note);
                } else if note.len() == 7 {
                    numbers.insert(8, note);
                }
            }

            for note in notes.clone() {
                if note.len() == 6 {
                    let common_count_to_four = calc_common_to(&4, note, &numbers);
                    if common_count_to_four == 4 {
                        numbers.insert(9, note);
                    } else {
                        let common_count_to_one = calc_common_to(&1, note, &numbers);
                        if common_count_to_one == 2 {
                            numbers.insert(0, note);
                        } else {
                            numbers.insert(6, note);
                        }
                    }
                } else if note.len() == 5 {
                    let common_count_to_one = calc_common_to(&1, note, &numbers);
                    if common_count_to_one == 2 {
                        numbers.insert(3, note);
                    } else {
                        let common_count_to_four = calc_common_to(&4, note, &numbers);
                        if common_count_to_four == 3 {
                            numbers.insert(5, note);
                        } else {
                            numbers.insert(2, note);
                        }
                    }
                }
            }

            let lookup: HashMap<String, u8> = numbers.iter().clone()
                .map(|(n, str)| (str, n))
                .fold(HashMap::new(), |mut acc, (&key, &val)| {
                    let mut key_arr: Vec<char> = key.chars().collect();
                    key_arr.sort_by(|a, b| a.cmp(b));
                    let key_sorted = key_arr.into_iter().collect();
                    acc.insert(key_sorted, val);
                    acc
                });

            number_strs.iter()
                .map(|&s| {
                    let mut s_arr: Vec<char> = s.chars().collect();
                    s_arr.sort_by(|a, b| a.cmp(b));
                    s_arr.into_iter().collect::<String>()
                })
                .map(|str| {
                    lookup.get(&str).unwrap()
                })
                .fold(0usize, |acc, &val| acc * 10 + usize::from(val))
        })
        .sum()
}

fn calc_common_to(key: &u8, note: &str, lookup: &HashMap<u8, &str>) -> usize {
    let to_compare_to = lookup.get(key).unwrap().chars().fold(HashSet::new(), |mut acc, c| {
        acc.insert(c);
        acc
    });
    note.chars()
        .fold(HashSet::new(), |mut acc, c| {
            acc.insert(c);
            acc
        })
        .intersection(&to_compare_to)
        .count()
}


#[cfg(test)]
mod tests {
    use super::*;

    const TEST_INPUT: &str = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\nedbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\nfgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\nfbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\naecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\nfgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\ndbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\nbdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\negadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\ngcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce";

    #[test]
    fn test_a_example() {
        assert_eq!(26, task_a(TEST_INPUT))
    }

    #[test]
    fn test_b_example() {
        assert_eq!(61229, task_b(TEST_INPUT))
    }
}