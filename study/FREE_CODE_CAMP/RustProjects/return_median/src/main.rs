fn main() {
    let mut vec: Vec<f32> = Vec::new();
    for i in 0..=20 {
        let counter = i as f32;
        vec.push(counter * 5.0);
    }
    println!("Our vector is: ");
    for i in &vec {
        print!(" {i}");
    }
    let (mean, median) = return_median(&vec);
    println!("\nOur mean is: {mean} and our median is: {median}");



}

fn return_median(vec : &Vec<f32>) -> (f32, f32)  {

    let mut total = 0.0;
    let mut count = 0;

    for number in vec{
        total += number;
        count += 1;
    };

    let f32_counter = count as f32;
    let mean : f32 = total/f32_counter;

    let median = if count % 2 == 1 {

        ( vec[count/2] + vec[(count/2) - 1] )/2.0

    } else {
        vec[count/2]/2.0
    };

    (mean, median)
    
}
