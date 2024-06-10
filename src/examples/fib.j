let fib = fn (n: int) ->
    if (n==0  || n==1) then n
    else fib(n-1) + fib(n-2)
    end
    : int end
in
    println(fib(10))
end