type Grid = [[(u32, bool); 10]; 10];

pub fn task_a(input: &str) -> i32 {
    let mut grid = grid_from(input);
    let mut flashes = 0;
    for _ in 0..100 {
        grid = execute_round(&grid);
        flashes += calc_flashed(&grid);
    }
    return flashes;
}

pub fn task_b(input: &str) -> usize {
    let mut grid = grid_from(input);
    let mut flashes = 0;
    let mut round = 0;
    while flashes != 100 {
        grid = execute_round(&grid);
        flashes = calc_flashed(&grid);
        round += 1;
    }
    return round;
}

fn execute_round(prev: &Grid) -> Grid {
    let state_cleared = map_grid(prev, |_, (prev_val, _), _| {
        (prev_val, false)
    });
    let increased = map_grid(&state_cleared, |_, (prev_val, will_flash), _| {
        if will_flash {
            (prev_val, will_flash)
        } else {
            let new_val = prev_val + 1;
            (new_val, new_val > 9)
        }
    });
    let mut next = increased;
    let mut diff_flashes = calc_flashed(&increased);
    while diff_flashes > 0 {
        let prev_flashes = calc_flashed(&next);
        next = map_grid(&next, |grd, (prev_val, should_flash), pos| {
            if should_flash {
                (0, true)
            } else {
                let neighbours_about_to_flash = u32::try_from(count_neighbours_by(|x| x > 9, pos, grd)).expect("Number of neighbours unexpectedly high");
                let new_val = prev_val + neighbours_about_to_flash;
                (new_val, new_val > 9)
            }
        });
        let next_flashes = calc_flashed(&next);
        diff_flashes = next_flashes - prev_flashes;
    }

    next
}

fn calc_flashed(grid: &Grid) -> i32 {
    grid.iter().flatten().fold(0, |acc, (_, flash)| if *flash { acc + 1 } else { acc })
}

fn map_grid(prev: &Grid, map_fn: fn(&Grid, (u32, bool), (usize, usize)) -> (u32, bool)) -> Grid {
    let mut next: Grid = [[(0, false); 10]; 10];
    for (y, row) in next.iter_mut().enumerate() {
        for (x, elem) in row.iter_mut().enumerate() {
            *elem = map_fn(prev, prev[y][x], (x, y));
        }
    }
    next
}

fn count_neighbours_by(predicate: fn(u32) -> bool, (x, y): (usize, usize), grid: &Grid) -> usize {
    vec![
        (x.checked_sub(1), y.checked_sub(1)),
        (Some(x), y.checked_sub(1)),
        (if x == 9 { None } else { Some(x + 1) }, y.checked_sub(1)),
        (x.checked_sub(1), Some(y)),
        (if x == 9 { None } else { Some(x + 1) }, Some(y)),
        (x.checked_sub(1), if y == 9 { None } else { Some(y + 1) }),
        (Some(x), if y == 9 { None } else { Some(y + 1) }),
        (if x == 9 { None } else { Some(x + 1) }, if y == 9 { None } else { Some(y + 1) }),
    ]
        .iter()
        .filter(|(x, y)| x.is_some() && y.is_some())
        .map(|(x, y)| (x.unwrap(), y.unwrap()))
        .map(|(x, y)| grid[y][x])
        .filter(|(e, _)| predicate(*e))
        .count()
}


fn grid_from(input: &str) -> Grid {
    let mut grid: Grid = [[(0, false); 10]; 10];
    for (y, row) in grid.iter_mut().enumerate() {
        for (x, elem) in row.iter_mut().enumerate() {
            let char_to_parse = input.lines().nth(y).unwrap().chars().nth(x).unwrap();
            let val = char_to_parse.to_digit(10).unwrap();
            *elem = (val, false);
        }
    }

    return grid;
}

#[cfg(test)]
mod tests {
    use super::*;

    const INPUT: &str = "5483143223\n2745854711\n5264556173\n6141336146\n6357385478\n4167524645\n2176841721\n6882881134\n4846848554\n5283751526";

    #[test]
    fn test_a_example() {
        assert_eq!(1656, task_a(INPUT))
    }

    #[test]
    fn test_b_example() {
        assert_eq!(195, task_b(INPUT))
    }

    #[test]
    fn test_grid_from() {
        let input = "1000000000\n0100000000\n0000000000\n0000000000\n0000000000\n0000000000\n0000000000\n0000000000\n0000000000\n0000000000";
        let expected: Grid = [
            [(1, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (1, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)],
            [(0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false), (0, false)]
        ];
        assert_eq!(expected, grid_from(input))
    }

    #[test]
    fn test_do_pass() {
        let input = "0000000000\n0000000000\n0000000000\n0000000000\n0000090000\n0000000000\n0000000000\n0000000000\n0000000000\n0000000000";
        let expected = "0000000000\n0000000000\n0000000000\n0000111000\n0000101000\n0000111000\n0000000000\n0000000000\n0000000000\n0000000000";

        assert_eq!(grid_from(input), execute_round(&grid_from(expected)));
    }

    #[test]
    fn test_get_neighbours_with_val() {
        let grid = grid_from(INPUT);
        assert_eq!(2, count_neighbours_by(|i| i == 8, (3, 0), &grid))
    }

    #[test]
    fn test_execute_round() {
        let grid = grid_from(INPUT);
        let round_1 = execute_round(&grid);
        let round_2: Grid = execute_round(&round_1);
        let round_3: Grid = execute_round(&round_2);
        let round_4: Grid = execute_round(&round_3);
        let round_5: Grid = execute_round(&round_4);
        // 35
        assert_eq!(35, calc_flashed(&round_2));
        assert_eq!(8, calc_flashed(&round_5));
    }

}