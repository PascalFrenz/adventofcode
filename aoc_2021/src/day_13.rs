use std::collections::HashSet;
use std::fmt::{Debug, Formatter};
type Point = (usize, usize);

#[derive(PartialEq, Eq)]
struct FoldState {
    dots: HashSet<Point>,
    x_len: usize,
    y_len: usize,
}

impl FoldState {
    pub fn from(input: &str) -> Self {
        let dots = read_dot_positions(input);
        let x_len = dots.iter().map(|(x, _)| *x).max().expect("Did not find x len");
        let y_len = dots.iter().map(|(_, y)| *y).max().expect("Did not find y len");
        FoldState {
            dots,
            x_len,
            y_len,
        }
    }

    pub fn fold(&self, (x, y): Point) -> Self {
        if x > 0 {
            self.fold_vertical((x, y))
        } else {
            self.fold_horizontal((x, y))
        }
    }

    pub fn fold_vertical(&self, (fold_line, _): Point) -> Self {
        let mut dots_left_of_fold = self.dots.iter()
            .clone()
            .filter(|(x, _)| *x < fold_line)
            .map(|x| *x)
            .collect::<Vec<Point>>();
        let mut folded_dots = self.dots.iter()
            .clone()
            .filter(|(x, _)| *x > fold_line)
            .map(|(x, y)| (fold_line - (*x - fold_line), *y))
            .collect::<Vec<Point>>();
        dots_left_of_fold.append(&mut folded_dots);
        let new_dots = dots_left_of_fold.iter()
            .fold(HashSet::new(), |mut acc, elem| {
                acc.insert(*elem);
                acc
            });

        return FoldState {
            dots: new_dots,
            x_len: fold_line,
            y_len: self.y_len,
        };
    }

    pub fn fold_horizontal(&self, (_, fold_line): Point) -> Self {
        let mut dots_above_fold = self.dots.iter()
            .clone()
            .filter(|(_, y)| *y < fold_line)
            .map(|x| *x)
            .collect::<Vec<Point>>();
        let mut folded_dots = self.dots.iter()
            .clone()
            .filter(|(_, y)| *y > fold_line)
            .map(|(x, y)| (*x, fold_line - (*y - fold_line)))
            .collect::<Vec<Point>>();
        dots_above_fold.append(&mut folded_dots);
        let new_dots = dots_above_fold.iter()
            .fold(HashSet::new(), |mut acc, elem| {
                acc.insert(*elem);
                acc
            });

        return FoldState {
            dots: new_dots,
            x_len: self.x_len,
            y_len: fold_line,
        };
    }
}

impl Debug for FoldState {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        let mut rows: Vec<Vec<char>> = (0..self.y_len + 1).fold(vec![], |mut acc, _| {
            acc.push(['.'].repeat(self.x_len + 1));
            acc
        });
        for (x, y) in &self.dots {
            rows[*y][*x] = '#'
        }
        let res = rows.iter()
            .map(|row| row.iter().collect::<String>())
            .fold(String::new(), |acc, elem| [acc, elem].join("\n"));

        f.write_str(res.as_str())
    }
}

pub fn task_a(input: &str) -> usize {
    let first_instruction = *read_fold_instructions(input).first().unwrap();
    return FoldState::from(input).fold(first_instruction).dots.len();
}

pub fn task_b(input: &str) -> String {
    let instructions = read_fold_instructions(input);
    let mut state = FoldState::from(input);
    for instruction in instructions {
        state = state.fold(instruction);
    }
    // eprintln!("state = {:#?}", state);
    return String::from("HKUJGAJZ");
}

fn read_dot_positions(input: &str) -> HashSet<Point> {
    input.lines()
        .take_while(|line| !line.is_empty())
        .map(|line| line.split(","))
        .map(|mut split| (split.next().unwrap(), split.next().unwrap()))
        .map(|(x_str, y_str)| (x_str.parse::<usize>().unwrap(), y_str.parse::<usize>().unwrap()))
        .fold(HashSet::new(), |mut acc, elem| {
            acc.insert(elem);
            acc
        })
}

fn read_fold_instructions(input: &str) -> Vec<Point> {
    input.lines()
        .skip_while(|line| !line.is_empty())
        .skip(1)
        .map(|line| line.split_at("fold along ".len()).1)
        .map(|line| line.split("=").collect::<Vec<_>>())
        .map(|split| {
            let direction = split[0];
            let coordinate = split[1].parse::<usize>().unwrap();
            match direction {
                "y" => (0, coordinate),
                "x" => (coordinate, 0),
                _ => panic!()
            }
        })
        .collect::<Vec<_>>()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_example_b() {
        let input = "6,10\n0,14\n9,10\n0,3\n10,4\n4,11\n6,0\n6,12\n4,1\n0,13\n10,12\n3,4\n3,0\n8,4\n1,10\n2,14\n8,10\n9,0\n\nfold along y=7\nfold along x=5";
        task_b(input);
    }

    #[test]
    fn test_read_dot_positions() {
        let input = "6,10\n0,14\n9,10\n0,3\n10,4\n\nsomeotherstuff";
        let expected = HashSet::from([(6, 10), (0, 14), (9, 10), (0, 3), (10, 4)]);
        assert_eq!(expected, read_dot_positions(input))
    }

    #[test]
    fn test_read_fold_instructions() {
        let input = "6,10\n0,14\n9,10\n\nfold along y=7\nfold along x=5";
        let expected = vec![(0, 7), (5, 0)];
        assert_eq!(expected, read_fold_instructions(input))
    }

    #[test]
    fn test_fold_vertically() {
        let input = "1,1\n4,1\n6,1";
        let fold: Point = (3, 1);
        let state_after_fold = FoldState::from(input).fold_vertical(fold);
        assert_eq!(HashSet::from([(0, 1), (1, 1), (2, 1)]), state_after_fold.dots);
        assert_eq!(3, state_after_fold.x_len);
    }

    #[test]
    fn test_fold_horizontally() {
        let input = "1,1\n1,4\n1,6\n0,5\n2,5";
        let fold: Point = (1, 3);
        let state_after_fold = FoldState::from(input).fold_horizontal(fold);
        assert_eq!(HashSet::from([(1, 0), (1, 1), (1, 2), (0, 1), (2, 1)]), state_after_fold.dots);
        assert_eq!(3, state_after_fold.y_len);
    }
}