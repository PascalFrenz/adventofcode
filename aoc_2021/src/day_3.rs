pub fn task_a(input: &str) -> u64 {
    let bits = parse_input(input);
    let bits_length = bits.get(0).unwrap().len();
    let (gamma, epsilon) = calc_gamma_epsilon(&bits, bits_length);

    return gamma * epsilon;
}

pub fn task_b(input: &str) -> u64 {
    let bits = parse_input(input);
    let bits_length = bits.get(0).unwrap().len();

    let generator_rating_vec: Vec<u64> = filter_vector(
        bits_length,
        &bits.clone(),
        |i, x| calc_most_common_at(i, x)
    );
    let scrubber_rating_vec: Vec<u64> = filter_vector(
        bits_length,
        &bits.clone(),
        |i, x| calc_most_common_at(i, x) ^ 1
    );

    let oxygen_generator_rating = reduce_to_number(generator_rating_vec);
    let co2_scrubber_rating = reduce_to_number(scrubber_rating_vec);

    return oxygen_generator_rating * co2_scrubber_rating;
}

fn reduce_to_number(vec: Vec<u64>) -> u64 {
    vec.into_iter().reduce(|acc, x| (acc << 1) + x).unwrap()
}

fn filter_vector(bits_length: usize, vec: &Vec<Vec<u64>>, bit_fn: fn(usize, &Vec<Vec<u64>>) -> u64) -> Vec<u64> {
    let mut internal_vec = vec.clone();
    let mut i = 0;
    let mut stop: bool = false;
    while i < bits_length && !stop {
        internal_vec = internal_vec.clone().into_iter().filter(|x| {
            let bit = bit_fn(i, &internal_vec);
            x[i] == bit
        }).collect();
        stop = internal_vec.len() == 1;
        i += 1;
    }
    internal_vec.into_iter().flatten().collect()
}

fn calc_gamma_epsilon(bits: &Vec<Vec<u64>>, bits_length: usize) -> (u64, u64) {
    let mut gamma: u64 = 0;
    let mut epsilon: u64 = 0;
    for i in 0..bits_length {
        gamma = (gamma << 1) + calc_most_common_at(i, &bits);
        epsilon = (epsilon << 1) + calc_most_common_at(i, &bits) ^ 1;
    }
    (gamma, epsilon)
}

fn parse_input(input: &str) -> Vec<Vec<u64>> {
    let bits: Vec<Vec<u64>> = input.lines()
        .map(|line| line.chars()
            .map(|c| if c.eq(&'0') { 0 } else { 1 })
            .collect::<Vec<u64>>()
        )
        .fold(Vec::new(), |mut acc, b| {
            acc.push(b);
            return acc;
        });
    bits
}

fn calc_most_common_at(position: usize, bits: &Vec<Vec<u64>>) -> u64 {
    let mut one_count: u64 = 0;
    for x in bits {
        one_count += x[position]
    }
    let bit_count = i64::try_from(bits.len()).unwrap();
    let ones = i64::try_from(one_count).unwrap();
    let one_is_most_common = (bit_count - ones * 2) <= 0;

    return if one_is_most_common {
        1
    } else {
        0
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_a_example_input() {
        let input = "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010";
        assert_eq!(task_a(input), 198)
    }

    #[test]
    fn test_b_example_input() {
        let input = "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010";
        assert_eq!(task_b(input), 230)
    }

    #[test]
    fn test_calc_most_common() {
        let bits = vec![
            vec![1, 1, 1],
            vec![1, 0, 1],
            vec![0, 0, 0]
        ];

        assert_eq!(1, calc_most_common_at(0, &bits));
        assert_eq!(0, calc_most_common_at(1, &bits));
        assert_eq!(1, calc_most_common_at(2, &bits));
    }
}