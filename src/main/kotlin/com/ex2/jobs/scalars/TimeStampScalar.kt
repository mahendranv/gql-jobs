package com.ex2.jobs.scalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingSerializeException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@DgsScalar(name = "timestamp")
class TimeStampScalar : Coercing<OffsetDateTime, String> {

    override fun serialize(tz: Any?): String {
        if (tz is OffsetDateTime) {
            return tz.format(DateTimeFormatter.ISO_DATE_TIME)
        }
        throw CoercingSerializeException("Not a valid date time: $tz")
    }

    override fun parseValue(tz: Any?): OffsetDateTime {
        return OffsetDateTime.parse(tz.toString(), DateTimeFormatter.ISO_DATE_TIME)
    }

    override fun parseLiteral(tz: Any?): OffsetDateTime {
        if (tz is StringValue) {
            return OffsetDateTime.parse(tz.value, DateTimeFormatter.ISO_DATE_TIME)
        }
        throw CoercingSerializeException("Not a valid date time: $tz")
    }
}