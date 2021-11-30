type SeatingArea = Vec<Vec<char>>;

pub trait FerryTerminalState {
    fn new(seating_area: &str) -> Self;

    fn empty_seats(&self) -> usize;
    fn occupied_seats(&self) -> usize;

    fn compute_next_state(&self) -> FerryTerminal;
}

pub struct FerryTerminal {
    waiting_area: SeatingArea,
    empty_seats: usize,
    occupied_seats: usize,
}

impl FerryTerminalState for FerryTerminal {
    fn new(seating_area: &str) -> Self {
        let waiting_area = parse_input(seating_area);
        let empty_seats = count_seats(&waiting_area, &'L');
        let occupied_seats = count_seats(&waiting_area, &'#');

        FerryTerminal { waiting_area, empty_seats, occupied_seats }
    }

    fn empty_seats(&self) -> usize {
        self.empty_seats
    }

    fn occupied_seats(&self) -> usize {
        self.occupied_seats
    }

    fn compute_next_state(&self) -> FerryTerminal {
        let mut new_input: String = String::new();
        for y in 0..self.waiting_area.len() {
            for x in 0..self.waiting_area[y].len() {
                let seat = self.waiting_area[y][x];
                if seat == '.' {
                    new_input.push('.');
                } else if seat == 'L' && number_occupied_neighbours(&self.waiting_area, x, y) == 0 {
                    new_input.push('#');
                } else if seat == '#' && number_occupied_neighbours(&self.waiting_area, x, y) >= 4 {
                    new_input.push('L');
                } else {
                    new_input.push(seat);
                }
            }
            new_input.push('\n');
        }

        return FerryTerminal::new(&new_input);
    }
}

fn number_occupied_neighbours(area: &SeatingArea, x: usize, y: usize) -> usize {
    let a = if x < (isize::MAX as usize) { x as isize } else { panic!("that's huge") };
    let b = if y < (isize::MAX as usize) { y as isize } else { panic!("that's huge") };
    let neighbours: [bool; 8] = [
        is_occupied(area, a, b - 1), // above
        is_occupied(area, a, b + 1), // below
        is_occupied(area, a - 1, b), // left
        is_occupied(area, a + 1, b), // right
        is_occupied(area, a - 1, b - 1), // top left
        is_occupied(area, a + 1, b - 1), // top right
        is_occupied(area, a - 1, b + 1), // bottom left
        is_occupied(area, a + 1, b + 1) // bottom right
    ];

    return neighbours.iter()
        .map(|&occ| if occ { 1 } else { 0 })
        .reduce(|acc, curr| acc + curr)
        .expect("could not accumulate number of neighbours");
}

fn is_occupied(area: &SeatingArea, x: isize, y: isize) -> bool {
    let result: bool;

    if y < 0 || x < 0 {
        result = false;
    } else {
        let ux = x as usize;
        let uy = y as usize;
        let xy_inside_bounds = uy < area.len() && ux < area[uy].len();
        if xy_inside_bounds {
            result = area[uy][ux] == '#';
        } else {
            result = false;
        }
    }

    return result;
}

fn parse_input(input: &str) -> SeatingArea {
    return input.lines()
        .map(|line| line.chars().collect::<Vec<char>>())
        .collect();
}

fn count_seats(area: &SeatingArea, seat: &char) -> usize {
    return area.iter()
        .map(|v| v.iter().filter(|&c| c == seat).count())
        .reduce(|acc, len| acc + len)
        .expect("Could not unwrap result count");
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_parse_input() {
        let expected = vec![
            vec!['L', 'L', 'L'],
            vec!['.', '.', 'L'],
            vec!['L', '.', 'L'],
        ];
        assert_eq!(parse_input("LLL\n..L\nL.L"), expected)
    }

    #[test]
    fn test_count_seats() {
        let expected = vec![
            vec!['L', 'L', 'L'],
            vec!['.', '.', 'L'],
            vec!['L', '.', 'L'],
        ];
        assert_eq!(count_seats(&expected, &'L'), 6);
        assert_eq!(count_seats(&expected, &'.'), 3);
    }
}