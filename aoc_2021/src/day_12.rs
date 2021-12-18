pub fn task_a(input: &str) -> usize {
    return 0;
}

pub fn task_b(input: &str) -> usize {
    return 0;
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_example_a() {
        let input = "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end";
        assert_eq!(10, task_a(input));
    }

    #[test]
    fn test_example_b() {
        let input = "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end";
        assert_eq!(10, task_b(input));
    }
}