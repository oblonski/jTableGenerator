# jTableGenerator
generates tables

## Latex

Generating tables in Latex can be cumbersome. The
FluentLatexTableBuilder lets you create a standard latex table easily.

Example:

<pre><code>String latexTable = new FluentLatexTableBuilder()
	.withCaption("this is a latex table")
	.withLabel("tab:latex table")
    .withColumns(Arrays.asList("scenario","time","distance")
    .withRow(Array.asList("ref","1000","12")
    .withRow(Arrays.asList("cordon", "2000", "123"))
    .build();
</code></pre>

or you can use Guava's Splitter to split entire rows directly

<pre><code>String columns = "scenario,time,distance";
String row1 = "ref,1000,12";

String latexTable = new FluentLatexTableBuilder()
	.withCaption("this is a latex table")
	.withLabel("tab:latex table")
    .withColumns(Splitter.on(',').trimResults().split(columns))
    .withRow(Splitter.on(',').trimResults().split(row1))
    .withRow(Arrays.asList("cordon", "2000", "123"))
    .build();
</code></pre>

Both of the examples build a String containing the latex table pretty much like this

<pre><code>\begin{table}
	\begin{center}
		\caption{this is the first table}
		\begin{tabular}{l | r | r}
			\hline
			scenario & time & distance \\
			\hline
			ref & 1000 & 12 \\
			cordon & 2000 & 123 \\
			\hline
		\end{tabular}
	\end{center}
	\label{tab:first table}
\end{table}
</code></pre>

###Format
The default column format is 'l | r | r'. You change it with

<code>.withFormat("| c | c | c |");</code>

##without code

<kbd>git clone https://github.com/oblonski/jTableGenerator.git</kbd>

<kbd>cd jTableGenerator</kbd>

<kbd>mvn package</kbd>

<kbd>java -jar target/jTableGenerator-1.0-SNAPSHOT.jar -in infile -out outfile -del delimiterInfile</kbd>

Example:

Assume an infile called scenario.txt which looks like this
<pre><code>scenario,time,distance
ref,1000,12
cordon,2000,123
</code></pre>

<kbd>java -jar target/jTableGenerator-1.0-SNAPSHOT.jar -in scenario.txt -out latexTableScenario.txt -del ,</kbd>

creates a file latexTableScenario.txt containing

<pre><code>\begin{table}
	\begin{center}
		\caption{this is the first table}
		\begin{tabular}{l | r | r}
			\hline
			scenario & time & distance \\
			\hline
			ref & 1000 & 12 \\
			cordon & 2000 & 123 \\
			\hline
		\end{tabular}
	\end{center}
	\label{tab:first table}
\end{table}
</code></pre>


 

