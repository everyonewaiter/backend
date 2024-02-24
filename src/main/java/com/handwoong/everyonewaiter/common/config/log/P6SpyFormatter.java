package com.handwoong.everyonewaiter.common.config.log;

import static org.hibernate.engine.jdbc.internal.FormatStyle.BASIC;
import static org.hibernate.engine.jdbc.internal.FormatStyle.DDL;
import static org.hibernate.engine.jdbc.internal.FormatStyle.HIGHLIGHT;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import java.util.Locale;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class P6SpyFormatter implements MessageFormattingStrategy {

	private static final String PRINT_FORMAT = "%s %d%s";
	private static final String MILLI_SECOND = "ms";

	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions.getActiveInstance()
				.setLogMessageFormat(this.getClass().getName());
	}

	@Override
	public String formatMessage(
			final int connectionId,
			final String now,
			final long elapsed,
			final String category,
			final String prepared,
			final String sql,
			final String url
	) {
		final StringBuilder sb = new StringBuilder();
		sb.append(String.format(PRINT_FORMAT, category, elapsed, MILLI_SECOND));
		if (StringUtils.hasText(sql)) {
			final String formatSql = format(category, sql);
			final String highlightSql = highlight(formatSql);
			sb.append(highlightSql);
		}
		return sb.toString();
	}

	private String format(final String category, final String sql) {
		return isFormat(category) ? createFormatSql(sql) : sql;
	}

	private boolean isFormat(final String category) {
		return Category.STATEMENT
				.getName()
				.equals(category);
	}

	private String createFormatSql(final String sql) {
		final String trimmedSQL = sql.trim().toLowerCase(Locale.ROOT);
		return FormatType.isDDL(trimmedSQL)
				? formatSqlWithStyle(DDL, sql)
				: formatSqlWithStyle(BASIC, sql);
	}

	private String highlight(final String sql) {
		return formatSqlWithStyle(HIGHLIGHT, sql);
	}

	private String formatSqlWithStyle(final FormatStyle style, final String sql) {
		return style.getFormatter()
				.format(sql);
	}
}
