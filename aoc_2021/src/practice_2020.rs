use std::collections::HashMap;
use std::fs;
use std::time::Instant;
use crate::read_input_file;
use crate::util::read_input_file;

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

pub fn task_10() {
    let start = Instant::now();
    let filename = "resources/input10.txt";
    println!("...reading contents of: {}", filename);
    let contents = fs::read_to_string(filename).expect("Something went wrong while reading the file");
    let done_reading_file = Instant::now();
    println!("done! took {}ns", done_reading_file.duration_since(start).as_nanos());
    println!("...parsing content into number vector");
    let mut numbers: Vec<i32> = parse_to_vector(contents);
    append_outlet_and_built_in_charger(&mut numbers);
    sort_asc(&mut numbers);
    let done_parsing_numbers = Instant::now();
    println!("done! took {}ns", done_parsing_numbers.duration_since(done_reading_file).as_nanos());
    println!("...calculating occurrences");
    let diff_occurrences = calc_diff_occurrences(&mut numbers);
    let done_calculating = Instant::now();
    println!("done! took {}ns", done_calculating.duration_since(done_parsing_numbers).as_nanos());
    println!("Occurrences of differences: {:?}", diff_occurrences);
    let result = diff_occurrences.get(&1).unwrap() * diff_occurrences.get(&3).unwrap();
    let done_all = Instant::now();
    println!("Result: {}", result);
    println!();
    println!("execution time overall: {}ms, {}ns", done_all.duration_since(start).as_millis(), done_all.duration_since(start).as_nanos());
}

fn calc_diff_occurrences(numbers: &mut Vec<i32>) -> HashMap<i32, i32> {
    let mut diff_occurrences: HashMap<i32, i32> = HashMap::new();
    for i in 0..numbers.len() - 1 {
        let curr = numbers.get(i).expect(format!("could not get number at idx {}", i).as_str());
        let next = numbers.get(i + 1).expect(format!("could not get number at idx {}", i).as_str());
        let diff = next - curr;
        diff_occurrences.insert(diff, diff_occurrences.get(&diff).unwrap_or_else(|| &0) + 1);
    }
    diff_occurrences
}

fn sort_asc(numbers: &mut Vec<i32>) {
    numbers.sort();
}

fn append_outlet_and_built_in_charger(numbers: &mut Vec<i32>) {
    numbers.append(&mut vec![0]);
    numbers.append(&mut vec![numbers.last().unwrap() + 3]);
}

fn parse_to_vector(contents: String) -> Vec<i32> {
    contents.lines().map(|s| s.parse().unwrap()).collect::<Vec<i32>>()
}

pub fn task_11() {
    let start = Instant::now();
    let input = read_input_file("resources/input11.txt");
    let result_state = calculate_stable_state(&input);
    println!("Result: {} occupied seats", result_state.occupied_seats());

    let end = Instant::now();
    println!("took {:?}ms", end.duration_since(start).as_millis());
}

fn calculate_stable_state(input: &String) -> FerryTerminal {
    let mut iterations = 0;
    let mut current_state = FerryTerminal::new(&input);
    let mut state_changed = true;
    while state_changed {
        let next_state = current_state.compute_next_state();
        state_changed = current_state.occupied_seats() != next_state.occupied_seats();
        current_state = if state_changed { next_state } else { current_state };
        iterations += 1;
    }
    println!("DONE! took {} iterations", iterations);

    return current_state;
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