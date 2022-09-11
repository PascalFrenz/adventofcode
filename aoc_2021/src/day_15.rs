use std::collections::HashMap;

pub fn task_a(input: &str) -> Option<usize> {
    let edges = input.lines()
        .map(|line| line.chars().map(|c| c.to_digit(10).unwrap()).collect::<Vec<u32>>())
        .collect::<Vec<_>>();

    if edges.len() < 1 || edges.len() != edges[0].len() {
        return None;
    }
    let nodes: Vec<Vec<(usize, usize)>> = (edges.len()..).map(|y| (edges.len()..).map(|x| (x, y)).collect()).collect::<Vec<_>>();

    let mut cave_map = nodes.iter()
        .map(|row|
            row.iter()
                .map(|&(x, y)| ((x, y), u32::MAX))
                .collect::<Vec<((usize, usize), u32)>>()
        )
        .fold(HashMap::new(), |mut acc, weighted_row| {
            for (pos, weight) in weighted_row {
                acc.insert(pos, weight);
            }
            acc
        });

    cave_map.entry((0, 0)).and_modify(|it| *it = 0); // we start at (0, 0) thus risk is 0 initially



    return Some(0);
}

pub fn task_b(input: &str) -> Option<usize> {
    return Some(0);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_example_a() {
        let input = "1163751742\n1381373672\n2136511328\n3694931569\n7463417111\n1319128137\n1359912421\n3125421639\n1293138521\n2311944581";
        assert_eq!(Some(40), task_a(input));
    }

    #[test]
    fn test_example_b() {
        let input = "";
        assert_eq!(Some(10), task_b(input));
    }
}