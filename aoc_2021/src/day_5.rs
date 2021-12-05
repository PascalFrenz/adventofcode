type Point = (usize, usize);
type Interval = (Point, Point);
type Diagram = Vec<Vec<u64>>;

pub fn task_a(input: &str) -> u64 {
    return calc_number_of_twos(input, 1000, 1000, false);
}

pub fn task_b(input: &str) -> u64 {
    return calc_number_of_twos(input, 1000, 1000, true);
}

fn calc_number_of_twos(input: &str, rows: usize, cols: usize, with_diagonal: bool) -> u64 {
    let mut diagram = create_diagram(rows, cols);
    let intervals = parse_intervals(input);

    for interval in intervals {
        let points = unfold(interval, with_diagonal);
        for (x, y) in points {
            diagram[y][x] += 1;
        }
    }

    return diagram.iter().fold(0u64, |acc, row| {
        let twos_in_row = row.iter().fold(0u64, |row_acc, &n| if n > 1 { row_acc + 1 } else { row_acc });
        acc + twos_in_row
    });
}

fn create_diagram(rows: usize, cols: usize) -> Diagram {
    let mut diagram: Diagram = vec![];
    for _ in 0..rows {
        let mut row: Vec<u64> = vec![];
        for _ in 0..cols {
            row.push(0);
        }
        diagram.push(row);
    }
    diagram
}

fn unfold(interval: Interval, with_diagonal: bool) -> Vec<Point> {
    let ((x1, y1), (x2, y2)) = interval;
    let vertical_line = x1 == x2;
    let horizontal_line = y1 == y2;
    
    let mut points = vec![];
    if vertical_line {
        unfold_vertical(interval, &mut points)
    } else if horizontal_line {
        unfold_horizontal(interval, &mut points)
    } else if with_diagonal {
        unfold_diagonal(interval, &mut points)
    }

    return points
}

fn unfold_diagonal(interval: Interval, points: &mut Vec<Point>) {
    let ((x1, y1), (x2, y2)) = interval;
    let left_to_right = x1 < x2;
    let top_to_bottom = y1 < y2;
    if left_to_right {
        if top_to_bottom {
            for step in 0..(x2 - x1) + 1 {
                points.push((x1 + step, y1 + step));
            }
        } else {
            for step in 0..(x2 - x1) + 1 {
                points.push((x1 + step, y1 - step));
            }
        }
    } else {
        if top_to_bottom {
            for step in 0..(x1 - x2) + 1 {
                points.push((x1 - step, y1 + step));
            }
        } else {
            for step in 0..(x1 - x2) + 1 {
                points.push((x1 - step, y1 - step));
            }
        }
    }
}

fn unfold_horizontal(interval: Interval, points: &mut Vec<Point>) {
    let ((x1, y), (x2, _)) = interval;
    if x1 < x2 {
        push_fn(points, x1, x2, y, |c, v| (v, c));
    } else {
        push_fn(points, x2, x1, y, |c, v| (v, c));
    }
}

fn unfold_vertical(interval: Interval, points: &mut Vec<Point>) {
    let ((x, y1), (_, y2)) = interval;
    if y1 < y2 {
        push_fn(points, y1, y2, x, |c, v| (c, v));
    } else {
        push_fn(points, y2, y1, x, |c, v| (c, v));
    }
}
fn push_fn(pts: &mut Vec<Point>, lower: usize, upper: usize, constant: usize, f: fn(usize, usize) -> (usize, usize)) {
    for i in lower..upper + 1 {
        pts.push(f(constant, i));
    }
}

fn parse_intervals(input: &str) -> Vec<Interval> {
    return input.lines()
        .map(|line| parse_interval(line))
        .collect();
}

fn parse_interval(line: &str) -> Interval {
    let points = line.split(" -> ")
        .map(|s| parse_point(s))
        .map(|point| (point[0], point[1]))
        .collect::<Vec<Point>>();

    return (points[0], points[1]);
}

fn parse_point(s: &str) -> Vec<usize> {
    s.split(",")
        .map(|x| x.parse::<usize>().unwrap())
        .collect::<Vec<usize>>()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_task_a_example() {
        let input = "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2";
        let result = calc_number_of_twos(input, 10, 10, false);
        assert_eq!(5, result)
    }

    #[test]
    fn test_task_b_example() {
        let input = "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2";
        let result = calc_number_of_twos(input, 10, 10, true);
        assert_eq!(12, result)
    }

    #[test]
    fn test_parse_intervals() {
        let input = "1,2 -> 1,4\n4,0 -> 4,5";
        let expected = vec![((1, 2), (1, 4)), ((4, 0), (4, 5))];
        assert_eq!(expected, parse_intervals(input))
    }

    #[test]
    fn test_unfold_horizontal() {
        let ltr: Interval = ((0, 5), (5, 5));
        let rtl: Interval = ((0, 5), (5, 5));
        let expected: Vec<Point> = vec![(0, 5), (1, 5), (2, 5), (3, 5), (4, 5), (5, 5)];
        assert_eq!(expected, unfold(ltr, false));
        assert_eq!(expected, unfold(rtl, false));
    }

    #[test]
    fn test_unfold_vertical() {
        let ttp: Interval = ((5, 0), (5, 5));
        let btt: Interval = ((5, 5), (5, 0));
        let expected: Vec<Point> = vec![(5, 0), (5, 1), (5, 2), (5, 3), (5, 4), (5, 5)];
        assert_eq!(expected, unfold(ttp, false));
        assert_eq!(expected, unfold(btt, false));
    }

    #[test]
    fn test_unfold_diagonal() {
        let ttb_ltr: Interval = ((0, 0), (5, 5));
        let btt_ltr: Interval = ((0, 5), (5, 0));
        let ttb_rtl: Interval = ((5, 0), (0, 5));
        let btt_rtl: Interval = ((5, 5), (0, 0));
        let expected_ttb_ltr: Vec<Point> = vec![(0, 0), (1, 1), (2, 2), (3, 3), (4, 4), (5, 5)];
        let expected_btt_ltr: Vec<Point> = vec![(0, 5), (1, 4), (2, 3), (3, 2), (4, 1), (5, 0)];
        let expected_ttb_rtl: Vec<Point> = vec![(5, 0), (4, 1), (3, 2), (2, 3), (1, 4), (0, 5)];
        let expected_btt_rtl: Vec<Point> = vec![(5, 5), (4, 4), (3, 3), (2, 2), (1, 1), (0, 0)];
        assert_eq!(expected_ttb_ltr, unfold(ttb_ltr, true));
        assert_eq!(expected_btt_ltr, unfold(btt_ltr, true));
        assert_eq!(expected_ttb_rtl, unfold(ttb_rtl, true));
        assert_eq!(expected_btt_rtl, unfold(btt_rtl, true));
    }
}