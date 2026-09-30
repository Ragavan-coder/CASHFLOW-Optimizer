# Financial Cash Flow Optimization Engine

## Project Overview
A complete Core Java engine designed to simplify complex networks of debts using Data Structures and Algorithms (DSA). It computes an optimized settlement plan, minimizing the number of transactions and associated fees.

## Architecture
The system parses debts into a directed weighted graph. It calculates net balances, filtering out those settled. Debtors and creditors are sorted via a Custom Min/Max Heap to aggressively match the largest imbalances first (Greedy approach). For small participant counts, it uses exact Backtracking. 

## Algorithms Used
* **Greedy Algorithm**: Extracts largest debtors and creditors to match, reducing total transactions drastically. O(P log P) where P is participant count.
* **Backtracking**: Exact optimization minimizing transaction count. Exponential runtime; limited to small graphs.
* **Dijkstra's Algorithm**: For payment routing optimization across network accounts.
* **Heaps/Priority Queues**: Sorting transactions by urgency/deadlines.

## Constraints
Features like transfer limits, currency conversion rates, and tiered transactional fees are seamlessly integrated into the evaluation.

## How to Compile & Run
```bash
# Compile
mkdir out
javac -d out src/*.java

# Run Web Server
java -cp out Main

# Run Terminal Interface
java -cp out Main terminal

# DSA Demonstration
java -cp out Main dsa

# Benchmark
java -cp out Main benchmark

# Interview Mode Walkthrough
java -cp out Main interview
```

# CASHFLOW-Optimizer
