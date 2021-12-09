use std::collections::HashSet;
use crate::day_9::Direction::*;

type Grid = Vec<Vec<i32>>;
type Point = (usize, usize);
type Basin = HashSet<Point>;

enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN,
}

pub fn task_a(input: &str) -> i32 {
    let grid = parse_input(input);
    let low_points = calc_low_points(&grid);
    let risk_level: i32 = low_points.iter().map(|&(x, y)| grid[y][x] + 1).sum();

    return risk_level;
}

pub fn task_b(input: &str) -> usize {
    let grid = parse_input(input);
    let low_points = calc_low_points(&grid);
    let mut basins = calc_basins_length(&grid, low_points);
    basins.sort_by(|a, b| b.cmp(a));

    multiply_largest_three(&mut basins)
}

fn multiply_largest_three(basins: &mut Vec<usize>) -> usize {
    basins.iter().take(3).fold(1, |acc, &n| n * acc)
}

fn calc_basins_length(grid: &Grid, low_points: Vec<Point>) -> Vec<usize> {
    low_points.iter()
        .map(|&low_point| calc_basin(low_point, &grid))
        .map(|basin| basin.len())
        .collect::<Vec<usize>>()
}

fn calc_basin((x, y): Point, grid: &Grid) -> Basin {
    let next_val = grid[y][x];
    let (left, right, up, down) = get_neighbours((x, y), grid);

    let mut basin: Basin = HashSet::new();
    if left != 9 && left > next_val { basin = basin_union(basin, (x - 1, y), grid); }
    if right != 9 && right > next_val { basin = basin_union(basin, (x + 1, y), grid); }
    if up != 9 && up > next_val { basin = basin_union(basin, (x, y - 1), grid); }
    if down != 9 && down > next_val { basin = basin_union(basin, (x, y + 1), grid); }

    basin.insert((x, y));
    basin
}

fn basin_union(basin: Basin, point: Point, grid: &Grid) -> Basin {
    basin.union(&calc_basin(point, grid)).map(|&x| x).collect()
}

fn calc_low_points(grid: &Grid) -> Vec<Point>{
    let mut low_points: Vec<Point> = vec![];
    for y in 0..grid.len() {
        for x in 0..grid[y].len() {
            let point = (x, y);
            if all_neighbours_higher(point, &grid) {
                low_points.push(point);
            }
        }
    }
    low_points
}

fn parse_input(input: &str) -> Grid {
    if input.is_empty() {
        return vec![];
    } else {
        input.lines()
            .map(|line| line.chars()
                .map(|c| char::to_digit(c, 10))
                .map(|opt| opt.map(|n| if n < i32::MAX as u32 { i32::try_from(n).map_err(|_| "should not happen") } else { Err("Value was outside of i32::MAX") }))
                .map(|opt| opt.unwrap())
                .filter(|res| res.is_ok())
                .map(|res| res.unwrap())
                .collect::<Vec<i32>>()
            )
            .collect::<Vec<Vec<i32>>>()
    }
}

fn get_neighbour_value(direction: Direction, (x, y): Point, grid: &Grid) -> i32 {
    return match direction {
        LEFT => if x == 0 { 9 } else { grid[y][x - 1] }
        RIGHT => if x == grid[y].len() - 1 { 9 } else { grid[y][x + 1] }
        UP => if y == 0 { 9 } else { grid[y - 1][x] }
        DOWN => if y == grid.len() - 1 { 9 } else { grid[y + 1][x] }
    };
}

fn get_neighbours(point: Point, grid: &Grid) -> (i32, i32, i32, i32) {
    (
        get_neighbour_value(LEFT, point, grid),
        get_neighbour_value(RIGHT, point, grid),
        get_neighbour_value(UP, point, grid),
        get_neighbour_value(DOWN, point, grid)
    )
}

fn all_neighbours_higher((x, y): Point, grid: &Grid) -> bool {
    let val_at_point = grid[y][x];
    let (left, right, up, down) = get_neighbours((x, y), grid);
    return val_at_point < left && val_at_point < right && val_at_point < up && val_at_point < down;
}

#[cfg(test)]
mod tests {
    use crate::day_9::Direction::*;
    use super::*;

    const INPUT: &str = "2199943210\n3987894921\n9856789892\n8767896789\n9899965678";

    #[test]
    fn test_a_example() {
        assert_eq!(15, task_a(INPUT))
    }

    #[test]
    fn test_b_example() {
        assert_eq!(1134, task_b(INPUT))
    }

    #[test]
    fn parse_input_into_grid() {
        let expected = vec![
            vec![2, 1, 9, 9, 9, 4, 3, 2, 1, 0],
            vec![3, 9, 8, 7, 8, 9, 4, 9, 2, 1],
            vec![9, 8, 5, 6, 7, 8, 9, 8, 9, 2],
            vec![8, 7, 6, 7, 8, 9, 6, 7, 8, 9],
            vec![9, 8, 9, 9, 9, 6, 5, 6, 7, 8],
        ];
        assert_eq!(expected, parse_input(INPUT))
    }

    #[test]
    fn test_neighbour_value() {
        let grid = parse_input(INPUT);
        assert_eq!(9, get_neighbour_value(LEFT, (0, 0), &grid));
        assert_eq!(9, get_neighbour_value(UP, (0, 0), &grid));
        assert_eq!(9, get_neighbour_value(RIGHT, (9, 4), &grid));
        assert_eq!(9, get_neighbour_value(DOWN, (9, 4), &grid));
        assert_eq!(8, get_neighbour_value(UP, (4, 2), &grid))
    }

    #[test]
    fn test_get_neighbours() {
        let grid = parse_input(INPUT);
        assert_eq!((9, 1, 9, 3), get_neighbours((0, 0), &grid))
    }

    #[test]
    fn test_all_neighbours_higher() {
        let grid = parse_input(INPUT);
        assert_eq!(true, all_neighbours_higher((1, 0), &grid));
    }

    #[test]
    fn test_calc_basin() {
        let grid = parse_input(INPUT);
        assert_eq!(HashSet::from([(1, 0), (0, 0), (0, 1)]), calc_basin((1, 0), &grid));
        assert_eq!(HashSet::from([
            (2, 1), (3, 1), (4, 1),
            (1, 2), (2, 2), (3, 2), (4, 2), (5, 2),
            (0, 3), (1, 3), (2, 3), (3, 3), (4, 3),
            (1, 4)
        ]), calc_basin((2, 2), &grid));
    }
}