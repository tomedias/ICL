let fact = fn(n:int) ->
    if n == 0 then 1
    else n * fact(n-1)
    end
    : int end
in
    println fact(5)
end