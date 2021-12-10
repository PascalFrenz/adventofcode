use std::fmt::{Debug, Formatter};

use crate::day_10::LineState::*;

pub fn task_a(input: &str) -> usize {
    input.lines()
        .map(|line| parse_line(line.chars().collect(), vec![]))
        .map(|state| match state {
            VALID => 0,
            INVALID(_) => 0,
            CORRUPTED(')') => 3,
            CORRUPTED(']') => 57,
            CORRUPTED('}') => 1197,
            CORRUPTED('>') => 25137,
            CORRUPTED(_) => 0,
        })
        .sum()
}

pub fn task_b(input: &str) -> i64 {
    let mut line_scores = input.lines()
        .map(|line| parse_line(line.chars().collect(), vec![]))
        .map(|state| match state {
            VALID => "".to_owned(),
            INVALID(s) => s,
            CORRUPTED(_) => "".to_owned()
        })
        .filter(|invalid_str| !invalid_str.is_empty())
        .map(|missing_str| missing_str.chars().fold(0i64, |score, first| {
            match first {
                ')' => score * 5 + 1,
                ']' => score * 5 + 2,
                '}' => score * 5 + 3,
                '>' => score * 5 + 4,
                _ => panic!()
            }
        }))
        .collect::<Vec<_>>();

    line_scores.sort();
    line_scores[line_scores.len() / 2]
}

#[derive(PartialEq, Eq)]
enum LineState {
    VALID,
    INVALID(String),
    CORRUPTED(char),
}

impl Debug for LineState {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        match self {
            VALID => f.write_str("Valid"),
            INVALID(s) => f.write_fmt(format_args!("Invalid. Missing: {}", s)),
            CORRUPTED(c) => f.write_fmt(format_args!("Corrupted. Expected: {}", c))
        }
    }
}

fn parse_line(input: Vec<char>, mut expected: Vec<char>) -> LineState {
    return if input.is_empty() && expected.is_empty() {
        VALID
    } else if input.is_empty() && !expected.is_empty() {
        INVALID(
            expected.iter()
                .rev()
                .fold(String::new(), |mut s, c| {
                    s.push(*c);
                    s
                })
        )
    } else {
        let (first, rest_input) = input.split_first().unwrap();
        if ['(', '{', '[', '<'].contains(first) {
            match *first {
                '(' => expected.push(')'),
                '{' => expected.push('}'),
                '[' => expected.push(']'),
                '<' => expected.push('>'),
                _ => { panic!() }
            }
            parse_line(rest_input.to_vec().clone(), expected.clone())
        } else {
            let (expectation, rest_expected) = expected.split_last().unwrap();
            if first != expectation {
                CORRUPTED(*first)
            } else {
                parse_line(rest_input.to_vec().clone(), rest_expected.to_vec().clone())
            }
        }
    };
}

#[cfg(test)]
mod tests {
    use super::*;

    const INPUT: &str = "[({(<(())[]>[[{[]{<()<>>\n[(()[<>])]({[<{<<[]>>(\n{([(<{}[<>[]}>{[]{[(<()>\n(((({<>}<{<{<>}{[]{[]{}\n[[<[([]))<([[{}[[()]]]\n[{[{({}]{}}([{[{{{}}([]\n{<[[]]>}<{[{[{[]{()[[[]\n[<(<(<(<{}))><([]([]()\n<{([([[(<>()){}]>(<<{{\n<{([{{}}[<[[[<>{}]]]>[]]";

    fn assert_eq_all<R, I>(input: Vec<(R, I)>, res_fn: fn(I) -> R) where R: PartialEq, R: Debug {
        for (expected, inpt) in input {
            assert_eq!(expected, res_fn(inpt))
        }
    }

    #[test]
    fn test_a_example() {
        assert_eq!(26397, task_a(INPUT));
    }

    #[test]
    fn test_b_example() {
        assert_eq!(288957, task_b(INPUT));
    }

    #[test]
    fn test_parse_line_valid() {
        let res_fn: fn(&str) -> LineState = |x| parse_line(x.chars().collect(), vec![]);
        assert_eq_all(vec![
            (VALID, ""),
            (VALID, "()"),
            (VALID, "(())"),
            (VALID, "(([]))"),
            (VALID, "(<[{}]>)"),
        ], res_fn);
    }

    #[test]
    fn test_parse_line_invalid() {
        let res_fn: fn(&str) -> LineState = |x| parse_line(x.chars().collect(), vec![]);
        assert_eq_all(vec![
            (INVALID((")").to_owned()), "("),
            (INVALID((">))").to_owned()), "((<"),
            (INVALID((")").to_owned()), "((<>)"),
        ], res_fn);
    }

    #[test]
    fn test_parse_line_corrupted() {
        let res_fn: fn(&str) -> LineState = |x| parse_line(x.chars().collect(), vec![]);
        assert_eq_all(vec![
            (CORRUPTED('>'), "(>"),
            (CORRUPTED(')'), "((<)}"),
            (CORRUPTED(']'), "((<>)]"),
        ], res_fn);
    }
}