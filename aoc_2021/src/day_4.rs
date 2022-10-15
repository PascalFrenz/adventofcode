type Number = (u64, bool);
type Board = Vec<Vec<Number>>;

struct Game {
    not_drawn_numbers: Vec<u64>,
    drawn_numbers: Vec<u64>,
    boards: Vec<Board>
}

trait Playable {
    fn new(input: &str) -> Self;
    fn do_turn(&self) -> Self;
    fn check_won(&self) -> Vec<(u64, u64, bool)>;
}

impl Playable for Game {
    fn new(input: &str) -> Game {
        let input_state: Vec<_> = input.split_whitespace().collect();
        let mut not_drawn_numbers: Vec<u64> = input_state.first()
            .unwrap()
            .split(',')
            .map(|n| n.parse().unwrap())
            .collect::<Vec<u64>>();
        not_drawn_numbers.reverse();
        let boards = Self::parse_boards(input_state.split_at(1).1.to_vec());
        return Game { not_drawn_numbers, drawn_numbers: Vec::new(), boards};
    }

    fn do_turn(&self) -> Game {
        let mut new_drawn_numbers = self.drawn_numbers.clone();
        let mut new_not_drawn_numbers = self.not_drawn_numbers.clone();
        let draw = new_not_drawn_numbers.pop().unwrap();
        new_drawn_numbers.push(draw);

        let new_boards: Vec<Board> = self.boards.iter()
            .map(|board| board.iter()
                .map(|row| row.iter()
                    .map(|&x| (x.0, x.1 || x.0 == draw)).collect()
                ).collect()
            ).collect();

        return Game {
            not_drawn_numbers: new_not_drawn_numbers,
            drawn_numbers: new_drawn_numbers,
            boards: new_boards
        }
    }

    fn check_won(&self) -> Vec<(u64, u64, bool)> {
        let mut boards_won: Vec<(u64, u64, bool)> = vec![];

        let mut b = 0;
        while b < self.boards.len() {
            let mut i = 0;
            let mut col_won = false;
            let mut row_won = false;
            let board = self.boards.get(b).unwrap();

            while i < board.len() && !col_won && !row_won {
                col_won |= board.iter().map(|row| row[i]).all(|n| n.1 == true);
                row_won |= board[i].iter().all(|&n| n.1 == true);
                i = if col_won || row_won { i } else { i + 1 };
            }
            if row_won || col_won {
                boards_won.push((
                    u64::try_from(b).unwrap(),
                    u64::try_from(i).unwrap(),
                    if col_won { true } else { false }
                ));
            }
            b += 1;
        }

        return boards_won;
    }
}

impl Game {
    fn parse_board(input: &str) -> Board {
        let mut line_iter = input.lines();
        let mut next = line_iter.next().unwrap();
        let mut board: Board = Vec::new();
        while !next.is_empty() {
            let board_row: Vec<Number> = next.split_ascii_whitespace()
                .filter(|s| !s.is_empty())
                .map(|n| (n.parse().unwrap(), false))
                .collect::<Vec<Number>>();
            board.push(board_row);
            next = line_iter.next().unwrap_or("");
        }

        return board;
    }

    fn parse_boards(boards_strs: Vec<&str>) -> Vec<Board> {
        let mut boards: Vec<Board> = Vec::new();
        for b in boards_strs {
            let board = Self::parse_board(b);
            boards.push(board);
        }

        return boards;
    }

    pub fn calc_sum_not_drawn(&self, board_idx: usize) -> u64 {
        let winner_board: &Board = self.boards.get(board_idx).unwrap();
        return winner_board.iter().fold(0, |sum, x|
            sum + x.iter().fold(0, |acc, &n| acc + if n.1 { 0 } else { n.0 })
        );
    }
}

pub fn task_a(input: &str) -> u64 {
    let mut game = Game::new(input);
    let mut win_state = vec![];
    while win_state.is_empty() {
        game = game.do_turn();
        win_state = game.check_won();
    }

    let board_idx = win_state[0].0 as usize;
    let sum_unmarked = game.calc_sum_not_drawn(board_idx);
    let just_drawn = game.drawn_numbers.pop().unwrap();
    sum_unmarked * just_drawn

}

pub fn task_b(input: &str) -> u64 {
    let mut game = Game::new(input);
    while game.boards.len() > 1 && game.not_drawn_numbers.len() > 1 {
        game = game.do_turn();
        let win_state = game.check_won();
        if !win_state.is_empty() {
            for i in 0..win_state.len() {
                game.boards.remove(win_state[i].0 as usize - i);
            }
        }
    }
    let mut win_state = vec![];
    while win_state.is_empty() {
        game = game.do_turn();
        win_state = game.check_won();
    }
    let last_won_board_idx = win_state[0].0 as usize;
    let last_board_opt = game.boards.get(last_won_board_idx);
    return if last_board_opt.is_some() {
        let sum_unmarked = game.calc_sum_not_drawn(last_won_board_idx);
        let just_drawn = game.drawn_numbers.pop().unwrap();
        sum_unmarked * just_drawn
    } else {
        0
    }
}

#[cfg(test)]
mod tests {

    use super::*;

    #[test]
    fn test_task_a_example() {
        let input = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n";
        assert_eq!(4512, task_a(input));
    }

    #[test]
    fn test_task_b_example() {
        let input = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n";
        assert_eq!(1924, task_b(input));
    }

    #[test]
    fn test_task_a_example_partial() {
        let input = "7,4,9,5,11\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7\n";
        let expected = vec![
            vec![
                vec![(22, false), (13, false), (17, false), (11, true), (0, false)],
                vec![(8, false), (2, false), (23, false), (4, true), (24, false)],
                vec![(21, false), (9, true), (14, false), (16, false), (7, true)],
                vec![(6, false), (10, false), (3, false), (18, false), (5, true)],
                vec![(1, false), (12, false), (20, false), (15, false), (19, false)]
            ],
            vec![
                vec![(3, false), (15, false), (0, false), (2, false), (22, false)],
                vec![(9, true), (18, false), (13, false), (17, false), (5, true)],
                vec![(19, false), (8, false), (7, true), (25, false), (23, false)],
                vec![(20, false), (11, true), (10, false), (24, false), (4, true)],
                vec![(14, false), (21, false), (16, false), (12, false), (6, false)]
            ],
            vec![
                vec![(14, false), (21, false), (17, false), (24, false), (4, true)],
                vec![(10, false), (16, false), (15, false), (9, true), (19, false)],
                vec![(18, false), (8, false), (23, false), (26, false), (20, false)],
                vec![(22, false), (11, true), (13, false), (6, false), (5, true)],
                vec![(2, false), (0, false), (12, false), (3, false), (7, true)]
            ]
        ];
        let mut game = Game::new(input);
        for _ in 0..5 {
            game = game.do_turn();
        }
        assert_eq!(expected, game.boards);
    }

    #[test]
    fn test_parse_board() {
        let input = "22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19";
        let expected: Board = vec![
            vec![(22, false), (13, false), (17, false), (11, false), (0, false)],
            vec![(8, false), (2, false), (23, false), (4, false), (24, false)],
            vec![(21, false), (9, false), (14, false), (16, false), (7, false)],
            vec![(6, false), (10, false), (3, false), (18, false), (5, false)],
            vec![(1, false), (12, false), (20, false), (15, false), (19, false)]
        ];

        assert_eq!(expected, Game::parse_board(input))
    }

    #[test]
    fn test_parse_boards() {
        let input = vec!["22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19", "1 2 3 4  5\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19"];
        let expected_b1: Board = vec![
            vec![(22, false), (13, false), (17, false), (11, false), (0, false)],
            vec![(8, false), (2, false), (23, false), (4, false), (24, false)],
            vec![(21, false), (9, false), (14, false), (16, false), (7, false)],
            vec![(6, false), (10, false), (3, false), (18, false), (5, false)],
            vec![(1, false), (12, false), (20, false), (15, false), (19, false)]
        ];
        let expected_b2: Board = vec![
            vec![(1, false), (2, false), (3, false), (4, false), (5, false)],
            vec![(8, false), (2, false), (23, false), (4, false), (24, false)],
            vec![(21, false), (9, false), (14, false), (16, false), (7, false)],
            vec![(6, false), (10, false), (3, false), (18, false), (5, false)],
            vec![(1, false), (12, false), (20, false), (15, false), (19, false)]
        ];

        let boards = Game::parse_boards(input);
        assert_eq!(vec![expected_b1, expected_b2], boards)
    }

    #[test]
    fn test_do_turn() {
        let game = Game {
            boards: vec![vec![vec![(22, false), (13, false), (17, false), (11, false), (0, false)]]],
            not_drawn_numbers: vec![22],
            drawn_numbers: vec![]
        };
        let game_after_turn = game.do_turn();
        let first_number = game_after_turn.boards[0][0][0];
        assert_eq!(true, first_number.1);
        assert_eq!(true, game_after_turn.drawn_numbers.contains(&22));
        assert_eq!(true, game_after_turn.not_drawn_numbers.is_empty());
    }

    #[test]
    fn test_check_won_row() {
        let game = Game {
            boards: vec![vec![
                vec![(22, true), (13, true), (17, true), (11, true), (0, true)],
                vec![(22, false), (13, true), (17, true), (11, true), (0, true)]
            ]],
            not_drawn_numbers: vec![22],
            drawn_numbers: vec![]
        };
        assert_eq!(vec![(0, 0, false)], game.check_won())
    }

    #[test]
    fn test_check_won_col() {
        let game = Game {
            boards: vec![vec![
                vec![(22, false), (13, true), (17, false), (11, true), (0, false)],
                vec![(22, false), (13, true), (17, true), (11, false), (0, true)]
            ]],
            not_drawn_numbers: vec![22],
            drawn_numbers: vec![]
        };
        assert_eq!(vec![(0, 1, true)], game.check_won())
    }
}