use std::cmp::Reverse;
use std::collections::{BinaryHeap};
use ndarray::{Array, Array2, Axis};

pub fn task_a(input: &str) -> u32 {
    let grid = parse(input);
    best_total_risk(&grid)
}

fn parse(input: &str) -> Array2<u32> {
    let lines: Vec<_> = input.lines().collect();
    let grid = Array::from_shape_vec(
        [lines.len(), lines[0].len()],
        input.chars().filter_map(|c| c.to_digit(10)).collect()
    ).unwrap();
    grid
}

fn best_total_risk(grid: &Array2<u32>) -> u32 {
    let mut best_known = Array::from_elem(grid.shape(), u32::MAX);
    let mut queue = BinaryHeap::from([(Reverse(0), [0, 0])]);
    while let Some((Reverse(total_risk), idx)) = queue.pop() {
        let best_known_risk = best_known.get_mut(idx).unwrap();
        if total_risk < *best_known_risk {
            *best_known_risk = total_risk;
            for adj in adjacent(idx) {
                if let Some(risk) = grid.get(adj) {
                    queue.push((Reverse(total_risk + risk), adj));
                }
            }
        }
    }
    *best_known.last().unwrap()
}

fn adjacent([x, y]: [usize; 2]) -> impl Iterator<Item = [usize; 2]> {
    [
        Some([x, y + 1]),
        Some([x + 1, y]),
        x.checked_sub(1).map(|x| [x, y]),
        y.checked_sub(1).map(|y| [x, y])
    ]
        .into_iter()
        .flatten()
}

pub fn task_b(input: &str) -> u32 {
    let grid = parse(input);
    let mut five_grids = grid.clone();
    for i in 1..=4 {
        five_grids
            .append(Axis(0), grid.mapv(|r| cycle(r + i)).view())
            .unwrap()
    }
    let mut twent_five_grids = five_grids.clone();
    for i in 1..=4 {
        twent_five_grids
            .append(Axis(1), five_grids.mapv(|r| cycle(r + i)).view())
            .unwrap()
    }
    best_total_risk(&twent_five_grids)
}

fn cycle(risk: u32) -> u32 {
    (risk - 1) % 9 + 1
}

#[cfg(test)]
mod tests {
    use super::*;

    const INPUT: &'static str = "1163751742\n1381373672\n2136511328\n3694931569\n7463417111\n1319128137\n1359912421\n3125421639\n1293138521\n2311944581";

    #[test]
    fn test_example_a() {
        assert_eq!(40, task_a(INPUT));
    }

    #[test]
    fn test_example_b() {
        assert_eq!(315, task_b(INPUT));
    }
}